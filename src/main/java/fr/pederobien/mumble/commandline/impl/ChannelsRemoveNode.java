package fr.pederobien.mumble.commandline.impl;

import java.util.function.Supplier;

import fr.pederobien.mumble.client.interfaces.IMumbleServer;

public class ChannelsRemoveNode extends MumbleClientNode {
	private ChannelsRemoveChannelNode channelNode;

	/**
	 * Creates a node in order to remove a channel from a server or players from a channel.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelsRemoveNode(Supplier<IMumbleServer> server) {
		super(server, "remove", EMumbleClientCode.MUMBLE__CHANNELS__REMOVE__EXPLANATION, s -> s != null);

		add(channelNode = new ChannelsRemoveChannelNode(server));
	}

	/**
	 * @return The node that removes channel from a server.
	 */
	public ChannelsRemoveChannelNode getChannelNode() {
		return channelNode;
	}
}
