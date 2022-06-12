package fr.pederobien.mumble.commandline.client.impl.player;

import java.util.function.Supplier;

import fr.pederobien.mumble.client.player.interfaces.IPlayerMumbleServer;
import fr.pederobien.mumble.commandline.client.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.client.impl.MumbleClientNode;

public class SetNode extends MumbleClientNode<IPlayerMumbleServer> {
	private SetMuteNode muteNode;
	private SetDeafenNode deafenNode;

	/**
	 * Creates a node in order to modify the properties of the main player and/or the properties of a player registered in a channel.
	 * 
	 * @param server The server associated to this node.
	 */
	protected SetNode(Supplier<IPlayerMumbleServer> server) {
		super(server, "set", EMumbleClientCode.MUMBLE__SET__EXPLANATION, s -> s != null && s.isJoined());

		add(muteNode = new SetMuteNode(server));
		add(deafenNode = new SetDeafenNode(server));
	}

	/**
	 * @return The node that update the mute status of the main player or the mute status of a player registered in a channel.
	 */
	public SetMuteNode getMuteNode() {
		return muteNode;
	}

	/**
	 * @return The node that update the deafen status of the main player.
	 */
	public SetDeafenNode getDeafenNode() {
		return deafenNode;
	}
}
