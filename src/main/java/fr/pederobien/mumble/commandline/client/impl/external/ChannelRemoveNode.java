package fr.pederobien.mumble.commandline.client.impl.external;

import java.util.function.Supplier;

import fr.pederobien.mumble.client.external.interfaces.IExternalMumbleServer;
import fr.pederobien.mumble.commandline.client.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.client.impl.MumbleClientNode;

public class ChannelRemoveNode extends MumbleClientNode<IExternalMumbleServer> {
	private ChannelRemoveChannelNode channelNode;
	private ChannelRemovePlayersNode playersNode;

	/**
	 * Creates a node in order to remove a channel from a server or players from a channel.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelRemoveNode(Supplier<IExternalMumbleServer> server) {
		super(server, "remove", EMumbleClientCode.MUMBLE__CHANNEL__REMOVE__EXPLANATION, s -> s != null);

		add(channelNode = new ChannelRemoveChannelNode(server));
		add(playersNode = new ChannelRemovePlayersNode(server));
	}

	/**
	 * @return The node that removes channel from a server.
	 */
	public ChannelRemoveChannelNode getChannelNode() {
		return channelNode;
	}

	/**
	 * @return The node that removes players from a channel.
	 */
	public ChannelRemovePlayersNode getPlayersNode() {
		return playersNode;
	}
}
