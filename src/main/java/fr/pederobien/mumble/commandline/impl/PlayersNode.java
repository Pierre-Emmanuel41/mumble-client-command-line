package fr.pederobien.mumble.commandline.impl;

import java.util.function.Supplier;

import fr.pederobien.mumble.client.interfaces.IMumbleServer;

public class PlayersNode extends MumbleClientNode {
	private PlayersAddNode addNode;
	private PlayersRemoveNode removeNode;
	private PlayersRenameNode renameNode;
	private PlayersOnlineNode onlineNode;
	private PlayersGameAddressNode gameAddressNode;
	private PlayersAdminNode adminNode;
	private PlayersMuteNode muteNode;
	private PlayersDeafenNode deafenNode;
	private PlayersMuteByNode muteByNode;

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
		add(onlineNode = new PlayersOnlineNode(server));
		add(gameAddressNode = new PlayersGameAddressNode(server));
		add(adminNode = new PlayersAdminNode(server));
		add(muteNode = new PlayersMuteNode(server));
		add(deafenNode = new PlayersDeafenNode(server));
		add(muteByNode = new PlayersMuteByNode(server));
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

	/**
	 * @return The node that updates the online status of a player on a server.
	 */
	public PlayersOnlineNode getOnlineNode() {
		return onlineNode;
	}

	/**
	 * @return The node that updates the game address of a player on a server.
	 */
	public PlayersGameAddressNode getGameAddressNode() {
		return gameAddressNode;
	}

	/**
	 * @return The node that updates the administrator status of a player on a server.
	 */
	public PlayersAdminNode getAdminNode() {
		return adminNode;
	}

	/**
	 * @return The node that updates the mute status of a player on a server.
	 */
	public PlayersMuteNode getMuteNode() {
		return muteNode;
	}

	/**
	 * @return The node that updates the deafen status of a player on a server.
	 */
	public PlayersDeafenNode getDeafenNode() {
		return deafenNode;
	}

	/**
	 * @return The node that mutes a player for another player.
	 */
	public PlayersMuteByNode getMuteByNode() {
		return muteByNode;
	}
}
