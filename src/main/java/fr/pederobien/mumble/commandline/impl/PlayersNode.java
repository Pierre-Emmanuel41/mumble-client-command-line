package fr.pederobien.mumble.commandline.impl;

import java.util.function.Supplier;

import fr.pederobien.mumble.client.interfaces.IMumbleServer;

public class PlayersNode extends MumbleClientNode {
	private PlayersAddNode addNode;
	private PlayersRemoveNode removeNode;
	private PlayersRenameNode renameNode;

	/**
	 * Creates a node to add, remove or update players properties.
	 * 
	 * @param server The server associated to this node.
	 */
	protected PlayersNode(Supplier<IMumbleServer> server) {
		super(server, "players", EMumbleClientCode.MUMBLE__PLAYERS__EXPLANATION, s -> s != null);

		add(addNode = new PlayersAddNode(server));
		add(removeNode = new PlayersRemoveNode(server));
		add(renameNode = new PlayersRenameNode(server));
	}

	/**
	 * @return The node that adds a player on a server.
	 */
	public PlayersAddNode getAddNode() {
		return addNode;
	}

	/**
	 * @return The node that removes a player from a server.
	 */
	public PlayersRemoveNode getRemoveNode() {
		return removeNode;
	}

	/**
	 * @return The node that renames a player on a server.
	 */
	public PlayersRenameNode getRenameNode() {
		return renameNode;
	}
}
