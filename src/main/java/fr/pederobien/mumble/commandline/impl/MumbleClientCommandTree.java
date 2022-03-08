package fr.pederobien.mumble.commandline.impl;

import fr.pederobien.commandtree.impl.CommandRootNode;
import fr.pederobien.commandtree.interfaces.ICommandRootNode;
import fr.pederobien.mumble.client.interfaces.IMumbleServer;
import fr.pederobien.mumble.commandline.interfaces.ICode;
import fr.pederobien.mumble.commandline.interfaces.IMumbleClientNode;

public class MumbleClientCommandTree {
	private IMumbleServer server;
	private ICommandRootNode<ICode> root;
	private IMumbleClientNode connectNode;

	public MumbleClientCommandTree() {
		root = new CommandRootNode<ICode>("mumble", EMumbleClientCode.MUMBLE__ROOT__EXPLANATION, () -> true);

		root.add(connectNode = new ConnectNode(this));
	}

	/**
	 * @return The underlying mumble server managed by this command tree.
	 */
	public IMumbleServer getServer() {
		return server;
	}

	/**
	 * Set the server managed by this command tree.
	 * 
	 * @param server The new server managed by this command tree.
	 */
	public void setServer(IMumbleServer server) {
		this.server = server;
	}

	/**
	 * @return The root of this command tree.
	 */
	public ICommandRootNode<ICode> getRoot() {
		return root;
	}

	/**
	 * @return The node that connect a mumble client to a remote.
	 */
	public IMumbleClientNode getConnectNode() {
		return connectNode;
	}
}
