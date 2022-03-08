package fr.pederobien.mumble.commandline.impl;

import java.util.function.Supplier;

import fr.pederobien.mumble.client.interfaces.IMumbleServer;

public class DisconnectNode extends MumbleClientNode {

	/**
	 * Creates a node that abort the connection with the remote.
	 * 
	 * @param server The server associated to this node.
	 */
	protected DisconnectNode(Supplier<IMumbleServer> server) {
		super(server, "disconnect", EMumbleClientCode.MUMBLE__DISCONNECT__EXPLANATION, s -> s != null);
	}

	@Override
	public boolean onCommand(String[] args) {
		if (getServer().isDisposed())
			return true;

		getServer().dispose();
		send(EMumbleClientCode.MUMBLE__DISCONNECT__CONNECTION_ABORTED, getServer().getAddress(), getServer().getPort());
		return true;
	}
}
