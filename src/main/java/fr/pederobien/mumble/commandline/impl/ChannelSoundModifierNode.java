package fr.pederobien.mumble.commandline.impl;

import java.util.function.Supplier;

import fr.pederobien.mumble.client.interfaces.IMumbleServer;

public class ChannelSoundModifierNode extends MumbleClientNode {
	private ChannelSoundModifierDetailsNode detailsNode;

	/**
	 * Creates a node in order to modify the sound modifier of a channel or the parameters of a sound modifier.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelSoundModifierNode(Supplier<IMumbleServer> server) {
		super(server, "soundModifier", EMumbleClientCode.MUMBLE__CHANNEL__SOUND_MODIFIER__EXPLANATION, s -> s != null);

		add(detailsNode = new ChannelSoundModifierDetailsNode(server));
	}

	/**
	 * @return The node that displays the property of the sound modifier associated to a channel.
	 */
	public ChannelSoundModifierDetailsNode getDetailsNode() {
		return detailsNode;
	}
}
