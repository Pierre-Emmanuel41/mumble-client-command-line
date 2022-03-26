package fr.pederobien.mumble.commandline.impl;

import java.util.function.Supplier;

import fr.pederobien.mumble.client.interfaces.IMumbleServer;

public class ChannelsAddNode extends MumbleClientNode {
	private ChannelsAddChannelNode channelNode;
	private ChannelsAddPlayersNode playersNode;

	/**
	 * Creates a node to add a channel to a server or players to a channel.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelsAddNode(Supplier<IMumbleServer> server) {
		super(server, "add", EMumbleClientCode.MUMBLE__CHANNELS__ADD__EXPLANATION, s -> s != null);

		add(channelNode = new ChannelsAddChannelNode(server));
		add(playersNode = new ChannelsAddPlayersNode(server));
	}

	/**
	 * @return The node that adds a channel to a server.
	 */
	public ChannelsAddChannelNode getChannelNode() {
		return channelNode;
	}

	/**
	 * @return The node that adds players to a channel.
	 */
	public ChannelsAddPlayersNode getPlayersNode() {
		return playersNode;
	}
}
