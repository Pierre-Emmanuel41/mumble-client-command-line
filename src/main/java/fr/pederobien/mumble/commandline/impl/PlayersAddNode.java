package fr.pederobien.mumble.commandline.impl;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import fr.pederobien.commandtree.exceptions.BooleanParseException;
import fr.pederobien.mumble.client.interfaces.IMumbleServer;
import fr.pederobien.mumble.client.interfaces.IResponse;

public class PlayersAddNode extends MumbleClientNode {
	private static final Pattern PATTERN = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

	/**
	 * Creates a node in order to add a player on a server.
	 * 
	 * @param server The server associated to this node.
	 */
	protected PlayersAddNode(Supplier<IMumbleServer> server) {
		super(server, "add", EMumbleClientCode.MUMBLE__PLAYERS__ADD__EXPLANATION, s -> s != null);
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		switch (args.length) {
		case 1:
			return asList(getMessage(EMumbleClientCode.MUMBLE__NAME__COMPLETION));
		case 2:
			Predicate<String> nameValid = name -> !getServer().getPlayers().get(name).isPresent();
			return check(args[0], nameValid, asList(getMessage(EMumbleClientCode.MUMBLE__ADDRESS_COMPLETION)));
		case 3:
			return check(args[1], address -> PATTERN.matcher(address).matches(), asList(getMessage(EMumbleClientCode.MUMBLE__PORT_COMPLETION)));
		case 4:
			Predicate<String> portValid = portStr -> {
				try {
					int port = Integer.parseInt(portStr);
					return 0 <= port && port <= 65535;
				} catch (NumberFormatException e) {
					return false;
				}
			};
			return check(args[2], portValid, asList(getMessage(EMumbleClientCode.MUMBLE__PLAYERS__ADD__ADMIN_COMPLETION)));
		case 5:
			Predicate<String> adminValid = isAdmin -> {
				try {
					return getBoolean(isAdmin);
				} catch (BooleanParseException e) {
					return false;
				}
			};
			return check(args[3], adminValid, asList(getMessage(EMumbleClientCode.MUMBLE__PLAYERS__ADD__X_COMPLETION)));
		case 6:
			return check(args[4], x -> isNotStrictDouble(x), asList(getMessage(EMumbleClientCode.MUMBLE__PLAYERS__ADD__Y_COMPLETION)));
		case 7:
			return check(args[5], y -> isNotStrictDouble(y), asList(getMessage(EMumbleClientCode.MUMBLE__PLAYERS__ADD__Z_COMPLETION)));
		case 8:
			return check(args[6], z -> isNotStrictDouble(z), asList(getMessage(EMumbleClientCode.MUMBLE__PLAYERS__ADD__YAW_COMPLETION)));
		case 9:
			return check(args[7], yaw -> isNotStrictDouble(yaw), asList(getMessage(EMumbleClientCode.MUMBLE__PLAYERS__ADD__PITCH_COMPLETION)));
		case 10:
			return check(args[7], yaw -> isNotStrictDouble(yaw), emptyList());
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(String[] args) {
		String name;
		try {
			name = args[0];
			if (getServer().getPlayers().get(name).isPresent()) {
				send(EMumbleClientCode.MUMBLE__PLAYERS__ADD__PLAYER_ALREADY_REGISTERED, name);
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__ADD__NAME_IS_MISSING);
			return false;
		}

		String address;
		try {
			address = args[1];
			if (!PATTERN.matcher(address).matches()) {
				send(EMumbleClientCode.MUMBLE__PLAYERS__ADD__ADDRESS_NOT_IPv4, name, address);
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__ADD__ADDRESS_IS_MISSING, name);
			return false;
		}

		int port;
		try {
			port = Integer.parseInt(args[2]);
			if (port < 0 || 65535 < port) {
				send(EMumbleClientCode.MUMBLE__PLAYERS__ADD__PORT_NUMBER_BAD_RANGE, name, port);
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__ADD__PORT_NUMBER_IS_MISSING, name);
			return false;
		} catch (NumberFormatException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__ADD__PORT_NUMBER_BAD_FORMAT, name, args[1]);
			return false;
		}

		boolean isAdmin;
		try {
			isAdmin = getBoolean(args[3]);
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__ADD__ADMIN_IS_MISSING, name);
			return false;
		} catch (BooleanParseException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__ADD__ADMIN_BAD_FORMAT, name);
			return false;
		}

		double x;
		try {
			x = getDouble(args[4]);
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__ADD__X_IS_MISSING, name);
			return false;
		} catch (NumberFormatException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__ADD__X_BAD_FORMAT, name);
			return false;
		}

		double y;
		try {
			y = getDouble(args[4]);
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__ADD__Y_IS_MISSING, name);
			return false;
		} catch (NumberFormatException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__ADD__Y_BAD_FORMAT, name);
			return false;
		}

		double z;
		try {
			z = getDouble(args[4]);
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__ADD__Z_IS_MISSING, name);
			return false;
		} catch (NumberFormatException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__ADD__Z_BAD_FORMAT, name);
			return false;
		}

		double yaw;
		try {
			yaw = getDouble(args[4]);
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__ADD__YAW_IS_MISSING, name);
			return false;
		} catch (NumberFormatException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__ADD__YAW_BAD_FORMAT, name);
			return false;
		}

		double pitch;
		try {
			pitch = getDouble(args[4]);
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__ADD__PITCH_IS_MISSING, name);
			return false;
		} catch (NumberFormatException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__ADD__PITCH_BAD_FORMAT, name);
			return false;
		}

		Consumer<IResponse> update = response -> {
			if (response.hasFailed())
				send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
			else
				send(EMumbleClientCode.MUMBLE__PLAYERS__ADD__REQUEST_SUCCEED, name);
		};

		InetSocketAddress gameAddress;
		try {
			gameAddress = new InetSocketAddress(InetAddress.getByName(address), port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		}

		getServer().getPlayers().add(name, gameAddress, isAdmin, x, y, z, yaw, pitch, update);
		return true;
	}
}
