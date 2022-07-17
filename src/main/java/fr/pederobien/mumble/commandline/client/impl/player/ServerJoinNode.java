package fr.pederobien.mumble.commandline.client.impl.player;

import java.util.function.Consumer;
import java.util.function.Supplier;

import fr.pederobien.messenger.interfaces.IResponse;
import fr.pederobien.mumble.client.player.interfaces.IPlayerMumbleServer;
import fr.pederobien.mumble.commandline.client.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.client.impl.MumbleClientNode;

public class ServerJoinNode extends MumbleClientNode<IPlayerMumbleServer> {

	/**
	 * Creates a node in order to let a player joining a mumble server.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ServerJoinNode(Supplier<IPlayerMumbleServer> server) {
		super(server, "join", EMumbleClientCode.MUMBLE__JOIN__EXPLANATION, s -> s != null && !s.isJoined());
	}

	@Override
	public boolean onCommand(String[] args) {
		Consumer<IResponse> update = response -> {
			if (response.hasFailed())
				send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
			else
				send(EMumbleClientCode.MUMBLE__JOIN__REQUEST_SUCCEED, getServer().getName());
		};

		try {
			send(EMumbleClientCode.MUMBLE__JOIN__ATTEMPTING_TO_JOIN, getServer().getName());
			getServer().join(update);
		} catch (IllegalStateException e) {
			send(EMumbleClientCode.MUMBLE__JOIN__REQUEST_ABORT, getServer().getName());
			return false;
		}
		return true;
	}
}
