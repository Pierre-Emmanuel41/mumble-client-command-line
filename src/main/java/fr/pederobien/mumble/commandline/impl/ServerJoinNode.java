package fr.pederobien.mumble.commandline.impl;

import java.util.function.Consumer;
import java.util.function.Supplier;

import fr.pederobien.mumble.client.external.interfaces.IResponse;
import fr.pederobien.mumble.commandline.interfaces.IMumbleServerType;

public class ServerJoinNode extends MumbleClientNode {

	/**
	 * Creates a node in order to let a player joining a mumble server.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ServerJoinNode(Supplier<IMumbleServerType> server) {
		super(server, "join", EMumbleClientCode.MUMBLE__JOIN__EXPLANATION, s -> s != null);

		setAvailable(() -> getServer() != null && getType() == ConnectionType.PLAYER_TO_SERVER && !getPlayerServer().isJoined());
	}

	@Override
	public boolean onCommand(String[] args) {
		Consumer<IResponse> update = response -> {
			if (response.hasFailed())
				send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
			else
				send(EMumbleClientCode.MUMBLE__JOIN__REQUEST_SUCCEED, getPlayerServer().getName());
		};

		try {
			send(EMumbleClientCode.MUMBLE__JOIN__ATTEMPTING_TO_JOIN, getPlayerServer().getName());
			getPlayerServer().join(update);
		} catch (IllegalStateException e) {
			send(EMumbleClientCode.MUMBLE__JOIN__REQUEST_ABORT, getPlayerServer().getName());
			return false;
		}
		return true;
	}
}
