package fr.pederobien.mumble.commandline.client.impl;

import java.util.function.Function;
import java.util.function.Supplier;

import fr.pederobien.commandline.CommandLineNode;
import fr.pederobien.commandline.ICode;
import fr.pederobien.mumble.client.common.interfaces.ICommonMumbleServer;

public class MumbleClientNode<T extends ICommonMumbleServer<?, ?, ?>> extends CommandLineNode {
	private Supplier<T> server;

	/**
	 * Creates a node specified by the given parameters.
	 * 
	 * @param server      The server associated to this node.
	 * @param label       The primary node name.
	 * @param explanation The explanation associated to this node.
	 * @param isAvailable True if this node is available, false otherwise.
	 */
	protected MumbleClientNode(Supplier<T> server, String label, ICode explanation, Function<T, Boolean> isAvailable) {
		super(label, explanation, () -> isAvailable.apply(server == null ? null : server.get()));
		this.server = server;
	}

	/**
	 * @return The server associated to this node.
	 */
	protected T getServer() {
		return server.get() == null ? null : server.get();
	}
}
