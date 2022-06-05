package fr.pederobien.mumble.commandline.client.impl.player;

import java.util.function.Consumer;
import java.util.function.Supplier;

import fr.pederobien.mumble.client.common.interfaces.IResponse;
import fr.pederobien.mumble.client.player.interfaces.IPlayerMumbleServer;
import fr.pederobien.mumble.commandline.client.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.client.impl.MumbleClientNode;

public class ServerLeaveNode extends MumbleClientNode<IPlayerMumbleServer> {

	/**
	 * Creates a node in order to let a player leaving a mumble server.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ServerLeaveNode(Supplier<IPlayerMumbleServer> server) {
		super(server, "leave", EMumbleClientCode.MUMBLE__LEAVE__EXPLANATION, s -> s != null && s.isJoined());
	}

	@Override
	public boolean onCommand(String[] args) {
		Consumer<IResponse> update = response -> {
			if (response.hasFailed())
				send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
			else
				send(EMumbleClientCode.MUMBLE__LEAVE__REQUEST_SUCCEED, getServer().getName());
		};

		send(EMumbleClientCode.MUMBLE__LEAVE__ATTEMPTING_TO_LEAVE, getServer().getName());
		getServer().leave(update);
		return true;
	}
}
