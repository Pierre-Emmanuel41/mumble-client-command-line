package fr.pederobien.mumble.commandline.impl;

import java.util.List;
import java.util.regex.Pattern;

import fr.pederobien.mumble.client.impl.GameMumbleServer;
import fr.pederobien.mumble.client.interfaces.IMumbleServer;

public class ConnectNode extends MumbleClientNode {
	private static final Pattern PATTERN = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
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
			return asList("<IP address>");
		case 2:
			return check(args[0], address -> PATTERN.matcher(address).matches(), asList("<port>"));
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(String[] args) {
		String address;
		try {
			address = args[0];
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__CONNECT__ADDRESS_IS_MISSING);
			return false;
		}

		if (!PATTERN.matcher(address).matches()) {
			send(EMumbleClientCode.MUMBLE__CONNECT__ADDRESS_NOT_IPv4, address);
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

		IMumbleServer server = new GameMumbleServer(String.format("MumbleServer_%s:%s", address, port), address, port);
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
