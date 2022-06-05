package fr.pederobien.mumble.commandline.impl.player;

import java.util.function.Consumer;

import fr.pederobien.commandtree.impl.CommandRootNode;
import fr.pederobien.commandtree.interfaces.ICommandRootNode;
import fr.pederobien.commandtree.interfaces.INode;
import fr.pederobien.mumble.client.player.interfaces.IPlayerMumbleServer;
import fr.pederobien.mumble.commandline.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.impl.MumbleClientCommandTree;
import fr.pederobien.mumble.commandline.interfaces.ICode;

public class PlayerMumbleClientRoot {
	private ICommandRootNode<ICode> root;
	private ServerJoinNode joinNode;
	private ServerLeaveNode leaveNode;
	private DetailsNode detailsNode;

	/**
	 * Creates a root node in order to modify an player mumble server.
	 * 
	 * @param tree      The command tree that contains the server to modify.
	 * @param displayer The consumer that specifies how to display the node explanation.
	 */
	public PlayerMumbleClientRoot(MumbleClientCommandTree tree, Consumer<INode<ICode>> displayer) {
		root = new CommandRootNode<ICode>("mumble", EMumbleClientCode.MUMBLE__ROOT__EXPLANATION, () -> true, displayer);

		root.add(joinNode = new ServerJoinNode(() -> (IPlayerMumbleServer) tree.getServer()));
		root.add(leaveNode = new ServerLeaveNode(() -> (IPlayerMumbleServer) tree.getServer()));
		root.add(detailsNode = new DetailsNode(() -> (IPlayerMumbleServer) tree.getServer()));
	}

	/**
	 * @return The root of the command tree in order to send request to an external mumble server.
	 */
	public ICommandRootNode<ICode> getRoot() {
		return root;
	}

	/**
	 * @return The node that lets a player joining a mumble server.
	 */
	public ServerJoinNode getJoinNode() {
		return joinNode;
	}

	/**
	 * @return The node that lets a player leaving a mumble server.
	 */
	public ServerLeaveNode getLeaveNode() {
		return leaveNode;
	}

	/**
	 * @return The node that displays the configuration of the current mumble server.
	 */
	public DetailsNode getDetailsNode() {
		return detailsNode;
	}
}
