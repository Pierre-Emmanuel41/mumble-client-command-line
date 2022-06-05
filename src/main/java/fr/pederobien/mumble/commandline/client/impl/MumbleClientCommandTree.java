package fr.pederobien.mumble.commandline.client.impl;

import java.util.Locale;
import java.util.function.Consumer;

import fr.pederobien.commandtree.impl.CommandRootNode;
import fr.pederobien.commandtree.interfaces.ICommandRootNode;
import fr.pederobien.commandtree.interfaces.INode;
import fr.pederobien.dictionary.impl.MessageEvent;
import fr.pederobien.mumble.client.common.interfaces.ICommonMumbleServer;
import fr.pederobien.mumble.client.external.interfaces.IExternalMumbleServer;
import fr.pederobien.mumble.commandline.client.impl.external.ExternalMumbleClientRoot;
import fr.pederobien.mumble.commandline.client.impl.player.PlayerMumbleClientRoot;
import fr.pederobien.mumble.commandline.client.interfaces.ICode;
import fr.pederobien.utils.AsyncConsole;

public class MumbleClientCommandTree {
	private ICommonMumbleServer<?, ?, ?> server;
	private ICommandRootNode<ICode> root;
	private ExternalMumbleClientRoot externalRoot;
	private PlayerMumbleClientRoot playerRoot;
	private ConnectNode connectNode;
	private DisconnectNode disconnectNode;

	public MumbleClientCommandTree() {
		Consumer<INode<ICode>> displayer = node -> {
			String label = node.getLabel();
			String explanation = MumbleClientDictionaryContext.instance().getMessage(new MessageEvent(Locale.getDefault(), node.getExplanation().toString()));
			AsyncConsole.println(String.format("%s - %s", label, explanation));
		};

		root = new CommandRootNode<ICode>("mumble", EMumbleClientCode.MUMBLE__ROOT__EXPLANATION, () -> true, displayer);
		externalRoot = new ExternalMumbleClientRoot(this, displayer);
		playerRoot = new PlayerMumbleClientRoot(this, displayer);

		root.add(connectNode = new ConnectNode(this));
		root.add(disconnectNode = new DisconnectNode(this));
	}

	/**
	 * @return The underlying mumble server managed by this command tree.
	 */
	public ICommonMumbleServer<?, ?, ?> getServer() {
		return server;
	}

	/**
	 * Set the server managed by this command tree.
	 * 
	 * @param server The new server managed by this command tree.
	 */
	public void setServer(ICommonMumbleServer<?, ?, ?> server) {
		if (this.server != null)
			this.server.close();

		// Disconnect
		if (server == null) {
			if (this.server instanceof IExternalMumbleServer)
				for (INode<?> node : externalRoot.getRoot().getChildren().values())
					root.remove(node.getLabel());
			else
				for (INode<?> node : playerRoot.getRoot().getChildren().values())
					root.remove(node.getLabel());
		}
		// Connect
		else {
			if (server instanceof IExternalMumbleServer)
				externalRoot.getRoot().export(root);
			else
				playerRoot.getRoot().export(root);
		}

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
	public ConnectNode getConnectNode() {
		return connectNode;
	}

	/**
	 * @return The node that aborts the connection with the remote.
	 */
	public DisconnectNode getDisconnectNode() {
		return disconnectNode;
	}
}
