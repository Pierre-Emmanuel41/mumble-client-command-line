package fr.pederobien.mumble.commandline.impl;

import java.util.Locale;
import java.util.function.Supplier;

import fr.pederobien.commandtree.impl.CommandNode;
import fr.pederobien.dictionary.impl.MessageEvent;
import fr.pederobien.mumble.client.interfaces.IMumbleServer;
import fr.pederobien.mumble.commandline.interfaces.ICode;
import fr.pederobien.mumble.commandline.interfaces.IMumbleClientNode;

public class MumbleClientNode extends CommandNode<ICode> implements IMumbleClientNode {
	private Supplier<IMumbleServer> server;

	/**
	 * Creates a node specified by the given parameters.
	 * 
	 * @param server      The server associated to this node.
	 * @param label       The primary node name.
	 * @param explanation The explanation associated to this node.
	 * @param isAvailable True if this node is available, false otherwise.
	 */
	protected MumbleClientNode(Supplier<IMumbleServer> server, String label, ICode explanation, Supplier<Boolean> isAvailable) {
		super(label, explanation, isAvailable);
		this.server = server;
	}

	/**
	 * Creates a node specified by the given parameters.
	 * 
	 * @param server      The server attached to this node.
	 * @param label       The primary node name.
	 * @param explanation The explanation associated to this node.
	 */
	protected MumbleClientNode(Supplier<IMumbleServer> server, String label, ICode explanation) {
		super(label, explanation);
		this.server = server;
	}

	/**
	 * @return The server associated to this node.
	 */
	protected IMumbleServer getServer() {
		return server.get();
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
}
