package fr.pederobien.mumble.commandline.impl;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import fr.pederobien.mumble.client.interfaces.IMumbleServer;
import fr.pederobien.mumble.client.interfaces.IResponse;

public class PlayersRenameNode extends MumbleClientNode {

	/**
	 * Creates a node that rename a player on the server
	 * 
	 * @param server The server associated to this node.
	 */
	protected PlayersRenameNode(Supplier<IMumbleServer> server) {
		super(server, "rename", EMumbleClientCode.MUMBLE__PLAYERS__RENAME__EXPLANATION, s -> s != null);
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		switch (args.length) {
		case 1:
			return filter(getServer().getPlayers().stream().map(player -> player.getName()), args);
		case 2:
			Predicate<String> nameValid = name -> getServer().getPlayers().get(name).isPresent();
			return check(args[0], nameValid, asList(getMessage(EMumbleClientCode.MUMBLE__NAME__COMPLETION)));
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(String[] args) {
		String oldName;
		try {
			oldName = args[0];
			if (!getServer().getPlayers().get(oldName).isPresent()) {
				send(EMumbleClientCode.MUMBLE__PLAYERS__RENAME__PLAYER_NOT_FOUND, oldName);
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__RENAME__NAME_IS_MISSING);
			return false;
		}

		String newName;
		try {
			newName = args[1];
			if (getServer().getPlayers().get(newName).isPresent()) {
				send(EMumbleClientCode.MUMBLE__PLAYERS__RENAME__PLAYER_ALREADY_REGISTERED, oldName, newName);
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__RENAME__NEW_NAME_IS_MISSING, oldName);
			return false;
		}

		Consumer<IResponse> update = response -> {
			if (response.hasFailed())
				send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
			else
				send(EMumbleClientCode.MUMBLE__PLAYERS__RENAME__REQUEST_SUCCEED, oldName, newName);
		};
		getServer().getPlayers().get(oldName).get().setName(newName, update);
		return true;
	}
}
