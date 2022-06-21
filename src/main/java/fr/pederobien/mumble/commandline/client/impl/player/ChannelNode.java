package fr.pederobien.mumble.commandline.client.impl.player;

import java.util.function.Supplier;

import fr.pederobien.mumble.client.player.interfaces.IPlayerMumbleServer;
import fr.pederobien.mumble.commandline.client.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.client.impl.MumbleClientNode;

public class ChannelNode extends MumbleClientNode<IPlayerMumbleServer> {
	private ChannelAddNode addNode;
	private ChannelRemoveNode removeNode;
	private ChannelRenameNode renameNode;
	private ChannelJoinNode joinNode;
	private ChannelLeaveNode leaveNode;
	private ChannelSoundModifierNode soundModifierNode;

	/**
	 * Creates a node that adds or removes channel from a mumble server or adds/removes players from a channel.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelNode(Supplier<IPlayerMumbleServer> server) {
		super(server, "channel", EMumbleClientCode.MUMBLE__CHANNEL__EXPLANATION, s -> s != null && s.isJoined());

		add(addNode = new ChannelAddNode(server));
		add(removeNode = new ChannelRemoveNode(server));
		add(renameNode = new ChannelRenameNode(server));
		add(joinNode = new ChannelJoinNode(server));
		add(leaveNode = new ChannelLeaveNode(server));
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
	 * @return The node in order to join a channel.
	 */
	public ChannelJoinNode getJoinNode() {
		return joinNode;
	}

	/**
	 * @return The node in order to leave a channel.
	 */
	public ChannelLeaveNode getLeaveNode() {
		return leaveNode;
	}

	/**
	 * @return The node that modifies the sound modifier of a channel or the parameters of a sound modifier.
	 */
	public ChannelSoundModifierNode getSoundModifierNode() {
		return soundModifierNode;
	}
}
