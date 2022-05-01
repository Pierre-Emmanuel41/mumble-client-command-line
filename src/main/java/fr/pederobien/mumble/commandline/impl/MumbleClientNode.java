package fr.pederobien.mumble.commandline.impl;

import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;

import fr.pederobien.commandtree.exceptions.NodeNotFoundException;
import fr.pederobien.commandtree.exceptions.NotAvailableArgumentException;
import fr.pederobien.commandtree.impl.CommandNode;
import fr.pederobien.dictionary.impl.MessageEvent;
import fr.pederobien.mumble.client.external.interfaces.IMumbleServer;
import fr.pederobien.mumble.client.external.interfaces.IPlayerMumbleServer;
import fr.pederobien.mumble.commandline.interfaces.ICode;
import fr.pederobien.mumble.commandline.interfaces.IMumbleClientNode;
import fr.pederobien.mumble.commandline.interfaces.IMumbleServerType;

public class MumbleClientNode extends CommandNode<ICode> implements IMumbleClientNode {
	private Supplier<IMumbleServerType> server;

	/**
	 * Creates a node specified by the given parameters.
	 * 
	 * @param server      The server associated to this node.
	 * @param label       The primary node name.
	 * @param explanation The explanation associated to this node.
	 * @param isAvailable True if this node is available, false otherwise.
	 */
	protected MumbleClientNode(Supplier<IMumbleServerType> server, String label, ICode explanation, Function<IMumbleServerType, Boolean> isAvailable) {
		super(label, explanation, () -> isAvailable.apply(server == null ? null : server.get()));
		this.server = server;
	}

	@Override
	public boolean onCommand(String[] args) {
		try {
			return super.onCommand(args);
		} catch (NodeNotFoundException e) {
			send(EMumbleClientCode.MUMBLE__NODE_NOT_FOUND, e.getNotFoundArgument());
		} catch (NotAvailableArgumentException e) {
			send(EMumbleClientCode.MUMBLE__NODE_NOT_AVAILABLE, e.getLabel());
		}
		return false;
	}

	/**
	 * @return The server associated to this node.
	 */
	protected IMumbleServer getServer() {
		return server.get() == null ? null : server.get().getServer();
	}

	/**
	 * @return The server associated to this node.
	 */
	protected IPlayerMumbleServer getPlayerServer() {
		return (IPlayerMumbleServer) (server.get() == null ? null : server.get().getServer());
	}

	/**
	 * @return The type of connection between this client and the remote.
	 */
	protected ConnectionType getType() {
		return server.get() == null ? null : server.get().getType();
	}

	/**
	 * Send a language sensitive message in the console.
	 * 
	 * @param code Used as key to get the right message in the right dictionary.
	 * @param args Some arguments (optional) used for dynamic messages.
	 */
	protected void send(ICode code, Object... args) {
		MumbleClientDictionaryContext.instance().send(new MessageEvent(Locale.getDefault(), code.getCode(), args));
	}

	/**
	 * Get a message associated to the given code translated in the OS language.
	 * 
	 * @param code Used as key to get the right message in the right dictionary.
	 * @param args Some arguments (optional) used for dynamic messages.
	 */
	protected String getMessage(ICode code, Object... args) {
		return MumbleClientDictionaryContext.instance().getMessage(new MessageEvent(Locale.getDefault(), code.getCode(), args));
	}

	/**
	 * This node is available if the server associated to this node is not null and if the connection type is
	 * {@link ConnectionType#PLAYER_TO_SERVER} then the server should have been joined by the player.
	 * 
	 * @return True if this node is available, false otherwise.
	 */
	protected boolean isAvailableAccordingServerProperties() {
		if (getType() == ConnectionType.EXTERNAL_GAME_SERVER_TO_SERVER)
			return getServer() != null;
		else
			return getPlayerServer() != null && getPlayerServer().isJoined();
	}
}
