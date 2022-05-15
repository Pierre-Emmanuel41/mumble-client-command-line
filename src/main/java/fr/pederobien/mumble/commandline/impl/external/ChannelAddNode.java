package fr.pederobien.mumble.commandline.impl.external;

import java.util.function.Supplier;

import fr.pederobien.mumble.client.external.interfaces.IExternalMumbleServer;
import fr.pederobien.mumble.commandline.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.impl.MumbleClientNode;

public class ChannelAddNode extends MumbleClientNode<IExternalMumbleServer> {
	private ChannelAddChannelNode channelNode;
	private ChannelAddPlayersNode playersNode;

	/**
	 * Creates a node to add a channel to a server or players to a channel.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelAddNode(Supplier<IExternalMumbleServer> server) {
		super(server, "add", EMumbleClientCode.MUMBLE__CHANNEL__ADD__EXPLANATION, s -> s != null);

		add(channelNode = new ChannelAddChannelNode(server));
		add(playersNode = new ChannelAddPlayersNode(server));
	}

	/**
	 * @return The node that adds a channel to a server.
	 */
	public ChannelAddChannelNode getChannelNode() {
		return channelNode;
	}

	/**
	 * @return The node that adds players to a channel.
	 */
	public ChannelAddPlayersNode getPlayersNode() {
		return playersNode;
	}
}
