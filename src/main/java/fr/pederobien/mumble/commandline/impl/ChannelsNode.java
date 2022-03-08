package fr.pederobien.mumble.commandline.impl;

import java.util.function.Supplier;

import fr.pederobien.mumble.client.interfaces.IMumbleServer;

public class ChannelsNode extends MumbleClientNode {
	private ChannelsAddNode addNode;
	private ChannelsRemoveNode removeNode;

	/**
	 * Creates a node that adds or removes channel from a mumble server.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelsNode(Supplier<IMumbleServer> server) {
		super(server, "channels", EMumbleClientCode.MUMBLE__CHANNELS__EXPLANATION, s -> s != null);

		add(addNode = new ChannelsAddNode(server));
		add(removeNode = new ChannelsRemoveNode(server));
	}

	/**
	 * @return The node that adds a channel to a server.
	 */
	public ChannelsAddNode getAddNode() {
		return addNode;
	}

	/**
	 * @return The node that removes a channel from a server.
	 */
	public ChannelsRemoveNode getRemoveNode() {
		return removeNode;
	}
}
