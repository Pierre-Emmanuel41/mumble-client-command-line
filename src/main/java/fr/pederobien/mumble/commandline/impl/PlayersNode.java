package fr.pederobien.mumble.commandline.impl;

import java.util.function.Supplier;

import fr.pederobien.mumble.client.interfaces.IMumbleServer;

public class PlayersNode extends MumbleClientNode {
	private PlayersAddNode addNode;

	/**
	 * Creates a node to add, remove or update players properties.
	 * 
	 * @param server The server associated to this node.
	 */
	protected PlayersNode(Supplier<IMumbleServer> server) {
		super(server, "players", EMumbleClientCode.MUMBLE__PLAYERS__EXPLANATION, s -> s != null);

		add(addNode = new PlayersAddNode(server));
	}

	/**
	 * @return The node that adds a player on a server.
	 */
	public PlayersAddNode getAddNode() {
		return addNode;
	}
}
