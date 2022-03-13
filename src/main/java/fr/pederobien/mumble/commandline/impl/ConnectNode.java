package fr.pederobien.mumble.commandline.impl;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.function.Predicate;

import fr.pederobien.mumble.client.impl.GameMumbleServer;
import fr.pederobien.mumble.client.interfaces.IMumbleServer;

public class ConnectNode extends MumbleClientNode {
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
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(String[] args) {
		InetAddress address;
		try {
			address = InetAddress.getByName(args[0]);
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

		if (getServer() != null)
			getServer().dispose();

		IMumbleServer server = new GameMumbleServer(String.format("MumbleServer_%s:%s", address.getHostAddress(), port), new InetSocketAddress(address, port));
		try {
			send(EMumbleClientCode.MUMBLE__CONNECT__ATTEMPTING_CONNECTION, address, port);
			server.open();
			send(EMumbleClientCode.MUMBLE__CONNECT__CONNECTION_COMPLETE, address, port);
			tree.setServer(server);
		} catch (IllegalStateException e) {
			send(EMumbleClientCode.MUMBLE__CONNECT__CONNECTION_ABORT, address, port);
			return false;
		}
		return true;
	}
}
