package fr.pederobien.mumble.commandline.client.impl;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.function.Predicate;

import fr.pederobien.mumble.client.common.interfaces.ICommonMumbleServer;
import fr.pederobien.mumble.client.external.impl.ExternalMumbleServer;
import fr.pederobien.mumble.client.player.event.MumbleGamePortCheckPostEvent;
import fr.pederobien.mumble.client.player.impl.PlayerMumbleServer;
import fr.pederobien.utils.event.EventHandler;
import fr.pederobien.utils.event.EventManager;
import fr.pederobien.utils.event.EventPriority;
import fr.pederobien.utils.event.IEventListener;

public class ConnectNode extends MumbleClientNode<ICommonMumbleServer<?, ?, ?>> {
	private MumbleClientCommandTree tree;
	private Listener listener;

	/**
	 * Creates a node in order to creates a new mumble server associated to a specific
	 * 
	 * @param tree The tree associated to this node.
	 */
	protected ConnectNode(MumbleClientCommandTree tree) {
		super(() -> tree.getServer(), "connect", EMumbleClientCode.MUMBLE__CONNECT__EXPLANATION, server -> true);
		this.tree = tree;

		listener = new Listener();
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

			List<String> connectionTypes = asList(Type.STANDALONE_SERVER.toString(), Type.SIMPLE_SERVER.toString());
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
			if (type == Type.UNKOWN) {
				send(EMumbleClientCode.MUMBLE__CONNECT__CONNECTION_TYPE_NOT_FOUND, ipAddress, port, args[2]);
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__CONNECT__CONNECTION_TYPE_IS_MISSING);
			return false;
		}

		if (args.length > 3 && args[3].equalsIgnoreCase("true"))
			listener.setRegistered(true);

		tree.setServer(null);

		try {
			String name = String.format("%s_Server", type);
			InetSocketAddress address = new InetSocketAddress(ipAddress, port);
			ICommonMumbleServer<?, ?, ?> server = type == Type.STANDALONE_SERVER ? new ExternalMumbleServer(name, address) : new PlayerMumbleServer(name, address);

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

	private class Listener implements IEventListener {
		private boolean isRegistered;

		private Listener() {
			isRegistered = false;
		}

		/**
		 * Register or unregister this listener for the EventManager.
		 * 
		 * @param isRegistered True to register, false to unregister.
		 */
		public void setRegistered(boolean isRegistered) {
			if (this.isRegistered == isRegistered)
				return;

			this.isRegistered = isRegistered;
			if (isRegistered)
				EventManager.registerListener(this);
			else
				EventManager.unregisterListener(this);
		}

		@EventHandler(priority = EventPriority.HIGH)
		private void onGamePortCheck(MumbleGamePortCheckPostEvent event) {
			if (!event.getServer().equals(getServer()))
				return;

			event.setUsed(true);
		}
	}
}
