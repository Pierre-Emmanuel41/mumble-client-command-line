package fr.pederobien.mumble.commandline.impl;

import java.net.InetSocketAddress;

import fr.pederobien.mumble.client.impl.GameMumbleServer;
import fr.pederobien.mumble.client.impl.PlayerMumbleServer;
import fr.pederobien.mumble.client.interfaces.IMumbleServer;
import fr.pederobien.mumble.commandline.interfaces.IMumbleServerType;

public class MumbleServerType implements IMumbleServerType {
	private IMumbleServer server;
	private ConnectionType type;

	/**
	 * Creates a server according to the value of the connection type.
	 * 
	 * @param name    The server name.
	 * @param address The server address.
	 * @param type    The type of connection between this client and the remote.
	 */
	public MumbleServerType(String name, InetSocketAddress address, ConnectionType type) {
		server = type == ConnectionType.EXTERNAL_GAME_SERVER_TO_SERVER ? new GameMumbleServer(name, address) : new PlayerMumbleServer(name, address);
		this.type = type;
	}

	@Override
	public IMumbleServer getServer() {
		return server;
	}

	@Override
	public ConnectionType getType() {
		return type;
	}

}