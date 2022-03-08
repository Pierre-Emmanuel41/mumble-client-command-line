package fr.pederobien.mumble.commandline.impl;

import java.util.Locale;
import java.util.function.Consumer;

import fr.pederobien.commandtree.impl.CommandRootNode;
import fr.pederobien.commandtree.interfaces.ICommandRootNode;
import fr.pederobien.commandtree.interfaces.INode;
import fr.pederobien.dictionary.impl.MessageEvent;
import fr.pederobien.mumble.client.interfaces.IMumbleServer;
import fr.pederobien.mumble.commandline.interfaces.ICode;
import fr.pederobien.mumble.commandline.interfaces.IMumbleClientNode;
import fr.pederobien.utils.AsyncConsole;

public class MumbleClientCommandTree {
	private IMumbleServer server;
	private ICommandRootNode<ICode> root;
	private IMumbleClientNode connectNode;
	private IMumbleClientNode stopNode;
	private IMumbleClientNode channelsNode;

	public MumbleClientCommandTree() {
		Consumer<INode<ICode>> displayer = node -> {
			String label = node.getLabel();
			String explanation = MumbleClientDictionaryContext.instance().getMessage(new MessageEvent(Locale.getDefault(), node.getExplanation().toString()));
			AsyncConsole.println(String.format("%s - %s", label, explanation));
		};

		root = new CommandRootNode<ICode>("mumble", EMumbleClientCode.MUMBLE__ROOT__EXPLANATION, () -> true, displayer);

		root.add(connectNode = new ConnectNode(this));
		root.add(stopNode = new StopNode(() -> getServer()));
		root.add(channelsNode = new ChannelsNode(() -> getServer()));
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
	 * @return The node that connects a mumble client to a remote.
	 */
	public IMumbleClientNode getConnectNode() {
		return connectNode;
	}

	/**
	 * @return The node that aborts the connection with the remote.
	 */
	public IMumbleClientNode getStopNode() {
		return stopNode;
	}

	/**
	 * @return The node that adds or removes channels from a server.
	 */
	public IMumbleClientNode getChannelsNode() {
		return channelsNode;
	}
}
