package fr.pederobien.mumble.commandline.impl;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import fr.pederobien.commandtree.exceptions.BooleanParseException;
import fr.pederobien.mumble.client.interfaces.IMumbleServer;
import fr.pederobien.mumble.client.interfaces.IResponse;

public class PlayersDeafenNode extends MumbleClientNode {

	/**
	 * Creates a node to deafen a player.
	 * 
	 * @param server The server associated to this node.
	 */
	protected PlayersDeafenNode(Supplier<IMumbleServer> server) {
		super(server, "deafen", EMumbleClientCode.MUMBLE__PLAYERS__DEAFEN__EXPLANATION, s -> s != null);
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		switch (args.length) {
		case 1:
			return filter(getServer().getPlayers().stream().map(player -> player.getName()), args);
		case 2:
			Predicate<String> nameValid = name -> getServer().getPlayers().get(name).isPresent();
			return check(args[0], nameValid, asList(getMessage(EMumbleClientCode.MUMBLE__PLAYERS__DEAFEN__COMPLETION)));
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
				send(EMumbleClientCode.MUMBLE__PLAYERS__DEAFEN__PLAYER_NOT_FOUND, name);
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__DEAFEN__NAME_IS_MISSING);
			return false;
		}

		boolean isDeafen;
		try {
			isDeafen = getBoolean(args[1]);
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__DEAFEN__STATUS_IS_MISSING, name);
			return false;
		} catch (BooleanParseException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__DEAFEN__STATUS_BAD_FORMAT, name);
			return false;
		}

		Consumer<IResponse> update = response -> {
			if (response.hasFailed())
				send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
			else if (isDeafen)
				send(EMumbleClientCode.MUMBLE__PLAYERS__DEAFEN__DEAFEND_REQUEST_SUCCEED, name);
			else
				send(EMumbleClientCode.MUMBLE__PLAYERS__DEAFEN__UNDEAFEND_REQUEST_SUCCEED, name);
		};
		getServer().getPlayers().get(name).get().setDeafen(isDeafen, update);
		return true;
	}
}
