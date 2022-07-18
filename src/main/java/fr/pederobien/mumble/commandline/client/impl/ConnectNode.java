package fr.pederobien.mumble.commandline.client.impl;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.function.Predicate;

import fr.pederobien.mumble.client.common.interfaces.ICommonMumbleServer;
import fr.pederobien.mumble.client.external.impl.ExternalMumbleServer;
import fr.pederobien.mumble.client.player.impl.PlayerMumbleServer;

public class ConnectNode extends MumbleClientNode<ICommonMumbleServer<?, ?, ?>> {
	private static final String EXTERNAL_MUMBLE_SERVER_NAME = "ExternalServer";
	private static final String PLAYER_MUMBLE_SERVER_NAME = "PlayerServer";
	private MumbleClientCommandTree tree;

	/**
	 * Creates a node in order to creates a new mumble server associated to a specific
	 * 
	 * @param tree The tree associated to this node.
	 */
	protected ConnectNode(MumbleClientCommandTree tree) {
		super(() -> tree.getServer(), "connect", EMumbleClientCode.MUMBLE__CONNECT__EXPLANATION, server -> true);
		this.tree = tree;
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		switch (args.length) {
		case 1:
			return asList(getMessage(EMumbleClientCode.MUMBLE__ADDRESS_COMPLETION));
		case 2:
			Predicate<String> addressValid = address -> {
				try {
					InetAddress.getByName(address);
				} catch (UnknownHostException e) {
					return false;
				}
				return true;
			};
			return check(args[0], addressValid, asList(getMessage(EMumbleClientCode.MUMBLE__PORT_COMPLETION)));
		case 3:
			Predicate<String> portValid = port -> {
				if (isStrictInt(port)) {
					int portValue = Integer.parseInt(port);
					return portValue < 0 || 65535 < portValue;
				}
				return false;
			};

			List<String> connectionTypes = asList(Type.EXTERNAL_TO_SERVER.toString(), Type.PLAYER_TO_SERVER.toString());
			return check(args[1], portValid, connectionTypes);
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(String[] args) {
		InetAddress ipAddress;
		try {
			ipAddress = InetAddress.getByName(args[0]);
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__CONNECT__ADDRESS_IS_MISSING);
			return false;
		} catch (UnknownHostException e) {
			send(EMumbleClientCode.MUMBLE__CONNECT__ADDRESS_NOT_FOUND, args[0]);
			return false;
		}

		int port;
		try {
			port = Integer.parseInt(args[1]);
			if (port < 0 || 65535 < port) {
				send(EMumbleClientCode.MUMBLE__CONNECT__PORT_NUMBER_BAD_RANGE, port);
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__CONNECT__PORT_NUMBER_IS_MISSING);
			return false;
		} catch (NumberFormatException e) {
			send(EMumbleClientCode.MUMBLE__CONNECT__PORT_NUMBER_BAD_FORMAT, args[1]);
			return false;
		}

		Type type;
		try {
			type = Type.getByName(args[2]);
			if (type == null) {
				send(EMumbleClientCode.MUMBLE__CONNECT__CONNECTION_TYPE_NOT_FOUND, ipAddress, port, args[2]);
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__CONNECT__CONNECTION_TYPE_IS_MISSING);
			return false;
		}

		tree.setServer(null);

		try {
			String name = type == Type.EXTERNAL_TO_SERVER ? EXTERNAL_MUMBLE_SERVER_NAME : PLAYER_MUMBLE_SERVER_NAME;

			InetSocketAddress address = new InetSocketAddress(ipAddress, port);
			ICommonMumbleServer<?, ?, ?> server = type == Type.EXTERNAL_TO_SERVER ? new ExternalMumbleServer(name, address) : new PlayerMumbleServer(name, address);

			send(EMumbleClientCode.MUMBLE__CONNECT__ATTEMPTING_CONNECTION, ipAddress, port);
			server.open();
			send(EMumbleClientCode.MUMBLE__CONNECT__CONNECTION_COMPLETE, ipAddress, port);
			tree.setServer(server);
		} catch (IllegalStateException e) {
			send(EMumbleClientCode.MUMBLE__CONNECT__CONNECTION_ABORT, ipAddress, port);
			return false;
		}
		return true;
	}
}
