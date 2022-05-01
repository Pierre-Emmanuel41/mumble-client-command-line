package fr.pederobien.mumble.commandline.interfaces;

import fr.pederobien.mumble.client.external.interfaces.IMumbleServer;
import fr.pederobien.mumble.commandline.impl.ConnectionType;

public interface IMumbleServerType {

	/**
	 * @return The underlying mumble server.
	 */
	IMumbleServer getServer();

	/**
	 * @return The type of connection between this client and the remote.
	 */
	ConnectionType getType();
}
