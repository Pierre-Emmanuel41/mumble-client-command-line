package fr.pederobien.mumble.commandline.client.impl.external;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import fr.pederobien.mumble.client.common.interfaces.IResponse;
import fr.pederobien.mumble.client.external.interfaces.IExternalMumbleServer;
import fr.pederobien.mumble.commandline.client.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.client.impl.MumbleClientNode;

public class PlayersRemoveNode extends MumbleClientNode<IExternalMumbleServer> {

	/**
	 * Creates a node that removes a player from the server.
	 * 
	 * @param server The server associated to this node.
	 */
	protected PlayersRemoveNode(Supplier<IExternalMumbleServer> server) {
		super(server, "remove", EMumbleClientCode.MUMBLE__PLAYERS__REMOVE__EXPLANATION, s -> s != null);
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		switch (args.length) {
		case 1:
			return filter(getServer().getPlayers().stream().map(player -> player.getName()), args);
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
				send(EMumbleClientCode.MUMBLE__PLAYERS__REMOVE__PLAYER_NOT_FOUND, name);
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__REMOVE__NAME_IS_MISSING);
			return false;
		}

		Consumer<IResponse> update = response -> {
			if (response.hasFailed())
				send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
			else
				send(EMumbleClientCode.MUMBLE__PLAYERS__REMOVE__REQUEST_SUCCEED, name);
		};
		getServer().getPlayers().remove(name, update);
		return true;
	}
}
