package fr.pederobien.mumble.commandline.impl;

import java.util.function.Supplier;

import fr.pederobien.mumble.client.interfaces.IMumbleServer;

public class ChannelNode extends MumbleClientNode {
	private ChannelAddNode addNode;
	private ChannelRemoveNode removeNode;
	private ChannelRenameNode renameNode;
	private ChannelSoundModifierNode soundModifierNode;

	/**
	 * Creates a node that adds or removes channel from a mumble server.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelNode(Supplier<IMumbleServer> server) {
		super(server, "channel", EMumbleClientCode.MUMBLE__CHANNELS__EXPLANATION, s -> s != null);

		add(addNode = new ChannelAddNode(server));
		add(removeNode = new ChannelRemoveNode(server));
		add(renameNode = new ChannelRenameNode(server));
		add(soundModifierNode = new ChannelSoundModifierNode(server));
	}

	/**
	 * @return The node that adds a channel to a server.
	 */
	public ChannelAddNode getAddNode() {
		return addNode;
	}

	/**
	 * @return The node that removes a channel from a server.
	 */
	public ChannelRemoveNode getRemoveNode() {
		return removeNode;
	}

	/**
	 * @return The channel that renames a channel on a server.
	 */
	public ChannelRenameNode getRenameNode() {
		return renameNode;
	}

	/**
	 * @return The node that modifies the sound modifier of a channel or the parameters of a sound modifier.
	 */
	public ChannelSoundModifierNode getSoundModifierNode() {
		return soundModifierNode;
	}
}
