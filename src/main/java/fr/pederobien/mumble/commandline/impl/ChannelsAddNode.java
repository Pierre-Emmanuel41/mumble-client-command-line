package fr.pederobien.mumble.commandline.impl;

import java.util.function.Supplier;

import fr.pederobien.mumble.client.interfaces.IMumbleServer;

public class ChannelsAddNode extends MumbleClientNode {
	private ChannelsAddChannelNode channelNode;

	/**
	 * Creates a node to add a channel to a server or players to a channel.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelsAddNode(Supplier<IMumbleServer> server) {
		super(server, "add", EMumbleClientCode.MUMBLE__CHANNELS__ADD__EXPLANATION, s -> s != null);

		add(channelNode = new ChannelsAddChannelNode(server));
	}

	/**
	 * @return The node that add a channel to a server.
	 */
	public ChannelsAddChannelNode getChannelNode() {
		return channelNode;
	}
}
