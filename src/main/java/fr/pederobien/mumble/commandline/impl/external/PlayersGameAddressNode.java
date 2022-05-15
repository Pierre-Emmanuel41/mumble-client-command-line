package fr.pederobien.mumble.commandline.impl.external;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import fr.pederobien.mumble.client.common.interfaces.IResponse;
import fr.pederobien.mumble.client.external.interfaces.IExternalMumbleServer;
import fr.pederobien.mumble.client.external.interfaces.IPlayer;
import fr.pederobien.mumble.commandline.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.impl.MumbleClientNode;

public class PlayersGameAddressNode extends MumbleClientNode<IExternalMumbleServer> {

	/**
	 * Creates a node in order to update the game address of a player.
	 * 
	 * @param server The server associated to this node.
	 */
	protected PlayersGameAddressNode(Supplier<IExternalMumbleServer> server) {
		super(server, "gameAddress", EMumbleClientCode.MUMBLE__PLAYERS__GAME_ADDRESS__EXPLANATION, s -> s != null);
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		switch (args.length) {
		case 1:
			return filter(getServer().getPlayers().stream().map(player -> player.getName()), args);
		case 2:
			return check(args[0], name -> getServer().getPlayers().get(name).isPresent(), asList(getMessage(EMumbleClientCode.MUMBLE__ADDRESS_COMPLETION)));
		case 3:
			Predicate<String> addressValid = address -> {
				try {
					InetAddress.getByName(address);
				} catch (UnknownHostException e) {
					return false;
				}
				return true;
			};
			return check(args[1], addressValid, asList(getMessage(EMumbleClientCode.MUMBLE__PORT_COMPLETION)));
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(String[] args) {
		String name;
		IPlayer player;
		try {
			name = args[0];
			Optional<IPlayer> optPlayer = getServer().getPlayers().get(name);
			if (!optPlayer.isPresent()) {
				send(EMumbleClientCode.MUMBLE__PLAYERS__GAME_ADDRESS__PLAYER_NOT_FOUND, name);
				return false;
			}
			player = optPlayer.get();
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__GAME_ADDRESS__NAME_IS_MISSING);
			return false;
		}

		InetAddress address;
		try {
			address = InetAddress.getByName(args[1]);
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__GAME_ADDRESS__ADDRESS_IS_MISSING, name);
			return false;
		} catch (UnknownHostException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__GAME_ADDRESS__ADDRESS_NOT_FOUND, name, args[1]);
			return false;
		}

		int port;
		try {
			port = Integer.parseInt(args[2]);
			if (port < 0 || 65535 < port) {
				send(EMumbleClientCode.MUMBLE__PLAYERS__GAME_ADDRESS__PORT_NUMBER_BAD_RANGE, name);
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__GAME_ADDRESS__PORT_NUMBER_IS_MISSING, name);
			return false;
		} catch (NumberFormatException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__GAME_ADDRESS__PORT_NUMBER_BAD_FORMAT, name);
			return false;
		}

		Consumer<IResponse> update = response -> {
			if (response.hasFailed())
				send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
			else {
				String gameAddress = player.getGameAddress().getAddress().getHostAddress();
				int gamePort = player.getGameAddress().getPort();
				send(EMumbleClientCode.MUMBLE__PLAYERS__GAME_ADDRESS__REQUEST_SUCCEED, name, gameAddress, gamePort);
			}
		};
		player.setGameAddress(new InetSocketAddress(address, port), update);
		return true;
	}
}
