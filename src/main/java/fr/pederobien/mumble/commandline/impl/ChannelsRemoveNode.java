package fr.pederobien.mumble.commandline.impl;

import java.util.function.Supplier;

import fr.pederobien.mumble.client.interfaces.IMumbleServer;

public class ChannelsRemoveNode extends MumbleClientNode {
	private ChannelsRemoveChannelNode channelNode;
	private ChannelsRemovePlayersNode playersNode;

	/**
	 * Creates a node in order to remove a channel from a server or players from a channel.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelsRemoveNode(Supplier<IMumbleServer> server) {
		super(server, "remove", EMumbleClientCode.MUMBLE__CHANNELS__REMOVE__EXPLANATION, s -> s != null);

		add(channelNode = new ChannelsRemoveChannelNode(server));
		add(playersNode = new ChannelsRemovePlayersNode(server));
	}

	/**
	 * @return The node that removes channel from a server.
	 */
	public ChannelsRemoveChannelNode getChannelNode() {
		return channelNode;
	}

	/**
	 * @return The node that removes players from a channel.
	 */
	public ChannelsRemovePlayersNode getPlayersNode() {
		return playersNode;
	}
}
