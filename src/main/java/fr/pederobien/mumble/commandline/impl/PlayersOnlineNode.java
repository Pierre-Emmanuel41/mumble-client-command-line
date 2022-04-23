package fr.pederobien.mumble.commandline.impl;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import fr.pederobien.commandtree.exceptions.BooleanParseException;
import fr.pederobien.mumble.client.interfaces.IResponse;
import fr.pederobien.mumble.commandline.interfaces.IMumbleServerType;

public class PlayersOnlineNode extends MumbleClientNode {

	/**
	 * Creates a node that update the online status of a player.
	 * 
	 * @param server The server associated to this node.
	 */
	protected PlayersOnlineNode(Supplier<IMumbleServerType> server) {
		super(server, "online", EMumbleClientCode.MUMBLE__PLAYERS__ONLINE__EXPLANATION, s -> s != null);

		setAvailable(() -> isAvailableAccordingServerProperties());
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		switch (args.length) {
		case 1:
			return filter(getServer().getPlayers().stream().map(player -> player.getName()), args);
		case 2:
			Predicate<String> nameValid = name -> getServer().getPlayers().get(name).isPresent();
			return check(args[0], nameValid, asList(getMessage(EMumbleClientCode.MUMBLE__PLAYERS__ONLINE__COMPLETION)));
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(String[] args) {
		String name;
		try {
			name = args[0];
			if (!getServer().getPlayers().get(name).isPresent()) {
				send(EMumbleClientCode.MUMBLE__PLAYERS__ONLINE__PLAYER_NOT_FOUND, name);
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__ONLINE__NAME_IS_MISSING);
			return false;
		}

		boolean isOnline;
		try {
			isOnline = getBoolean(args[1]);
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__ONLINE__STATUS_IS_MISSING, name);
			return false;
		} catch (BooleanParseException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__ONLINE__ONLINE_BAD_FORMAT, name);
			return false;
		}

		Consumer<IResponse> update = response -> {
			if (response.hasFailed())
				send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
			else if (isOnline)
				send(EMumbleClientCode.MUMBLE__PLAYERS__ONLINE__ONLINE_REQUEST_SUCCEED, name);
			else
				send(EMumbleClientCode.MUMBLE__PLAYERS__ONLINE__OFFLINE_REQUEST_SUCCEED, name);
		};
		getServer().getPlayers().get(name).get().setOnline(isOnline, update);
		return true;
	}
}
