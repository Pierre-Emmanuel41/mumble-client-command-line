package fr.pederobien.mumble.commandline.client.impl.external;

import java.util.function.Consumer;

import fr.pederobien.commandtree.impl.CommandRootNode;
import fr.pederobien.commandtree.interfaces.ICommandRootNode;
import fr.pederobien.commandtree.interfaces.INode;
import fr.pederobien.mumble.client.external.interfaces.IExternalMumbleServer;
import fr.pederobien.mumble.commandline.client.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.client.impl.MumbleClientCommandTree;
import fr.pederobien.mumble.commandline.client.interfaces.ICode;

public class ExternalMumbleClientRoot {
	private ICommandRootNode<ICode> root;
	private ChannelNode channelNode;
	private PlayersNode playersNode;
	private DetailsNode detailsNode;

	/**
	 * Creates a root node in order to modify an external mumble server.
	 * 
	 * @param tree      The command tree that contains the server to modify.
	 * @param displayer The consumer that specifies how to display the node explanation.
	 */
	public ExternalMumbleClientRoot(MumbleClientCommandTree tree, Consumer<INode<ICode>> displayer) {
		root = new CommandRootNode<ICode>("mumble", EMumbleClientCode.MUMBLE__ROOT__EXPLANATION, () -> true, displayer);

		root.add(channelNode = new ChannelNode(() -> (IExternalMumbleServer) tree.getServer()));
		root.add(playersNode = new PlayersNode(() -> (IExternalMumbleServer) tree.getServer()));
		root.add(detailsNode = new DetailsNode(() -> (IExternalMumbleServer) tree.getServer()));
	}

	/**
	 * @return The root of the command tree in order to send request to an external mumble server.
	 */
	public ICommandRootNode<ICode> getRoot() {
		return root;
	}

	/**
	 * @return The node that adds or removes channels from a server.
	 */
	public ChannelNode getChannelsNode() {
		return channelNode;
	}

	/**
	 * @return The node that adds, removes or update the properties of a player.
	 */
	public PlayersNode getPlayersNode() {
		return playersNode;
	}

	/**
	 * @return The node that displays the configuration of the current mumble server.
	 */
	public DetailsNode getDetailsNode() {
		return detailsNode;
	}
}
