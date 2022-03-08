package fr.pederobien.mumble.commandline.impl;

import java.util.function.Supplier;

import fr.pederobien.mumble.client.interfaces.IMumbleServer;

public class StopNode extends MumbleClientNode {

	/**
	 * Creates a node that abort the connection with the remote.
	 * 
	 * @param server The server associated to this node.
	 */
	protected StopNode(Supplier<IMumbleServer> server) {
		super(server, "stop", EMumbleClientCode.MUMBLE__STOP__EXPLANATION, s -> s != null);
	}

	@Override
	public boolean onCommand(String[] args) {
		getServer().close();
		return true;
	}
}
