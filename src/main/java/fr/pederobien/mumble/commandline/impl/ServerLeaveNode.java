package fr.pederobien.mumble.commandline.impl;

import java.util.function.Consumer;
import java.util.function.Supplier;

import fr.pederobien.mumble.client.interfaces.IResponse;
import fr.pederobien.mumble.commandline.interfaces.IMumbleServerType;

public class ServerLeaveNode extends MumbleClientNode {

	/**
	 * Creates a node in order to let a player leaving a mumble server.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ServerLeaveNode(Supplier<IMumbleServerType> server) {
		super(server, "leave", EMumbleClientCode.MUMBLE__LEAVE__EXPLANATION, s -> s != null);

		setAvailable(() -> getServer() != null && getType() == ConnectionType.PLAYER_TO_SERVER && getPlayerServer().isJoined());
	}

	@Override
	public boolean onCommand(String[] args) {
		Consumer<IResponse> update = response -> {
			if (response.hasFailed())
				send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
			else
				send(EMumbleClientCode.MUMBLE__LEAVE__REQUEST_SUCCEED, getPlayerServer().getName());
		};

		send(EMumbleClientCode.MUMBLE__LEAVE__ATTEMPTING_TO_LEAVE, getPlayerServer().getName());
		getPlayerServer().leave(update);
		return true;
	}
}
