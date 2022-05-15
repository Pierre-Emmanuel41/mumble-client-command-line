package fr.pederobien.mumble.commandline.impl.external;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.pederobien.mumble.client.common.interfaces.IResponse;
import fr.pederobien.mumble.client.external.exceptions.PlayerNotAdministratorException;
import fr.pederobien.mumble.client.external.exceptions.PlayerNotRegisteredInChannelException;
import fr.pederobien.mumble.client.external.interfaces.IChannel;
import fr.pederobien.mumble.client.external.interfaces.IExternalMumbleServer;
import fr.pederobien.mumble.client.external.interfaces.IPlayer;
import fr.pederobien.mumble.commandline.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.impl.MumbleClientNode;

public class PlayersKickNode extends MumbleClientNode<IExternalMumbleServer> {

	/**
	 * Creates a node in order to kick a player from a channel.
	 * 
	 * @param server The server associated to this node.
	 */
	protected PlayersKickNode(Supplier<IExternalMumbleServer> server) {
		super(server, "kick", EMumbleClientCode.MUMBLE__PLAYERS__KICK__EXPLANATION, s -> s != null);
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		switch (args.length) {
		case 1:
			return filter(getServer().getPlayers().stream().filter(player -> player.getChannel() != null).map(player -> player.getName()), args);
		case 2:
			Predicate<String> nameValid = name -> getServer().getPlayers().get(name).isPresent();
			Stream<IPlayer> players = getServer().getPlayers().stream().filter(player -> player.isAdmin());
			return check(args[0], nameValid, players.map(player -> player.getName()).collect(Collectors.toList()));
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(String[] args) {
		IPlayer kickedPlayer;
		try {
			Optional<IPlayer> optPlayer = getServer().getPlayers().get(args[0]);
			if (!optPlayer.isPresent()) {
				send(EMumbleClientCode.MUMBLE__PLAYERS__KICK__KICKED_PLAYER_NOT_FOUND, args[0]);
				return false;
			}

			kickedPlayer = optPlayer.get();
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__KICK__KICKED_PLAYER_NAME_IS_MISSING);
			return false;
		}

		IPlayer kickingPlayer;
		try {
			Optional<IPlayer> optPlayer = getServer().getPlayers().get(args[1]);
			if (!optPlayer.isPresent()) {
				send(EMumbleClientCode.MUMBLE__PLAYERS__KICK__KICKING_PLAYER_NOT_FOUND, kickedPlayer.getName(), args[1]);
				return false;
			}

			kickingPlayer = optPlayer.get();
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__KICK__KICKING_PLAYER_NAME_IS_MISSING, kickedPlayer.getName());
			return false;
		}

		IChannel channel = kickedPlayer.getChannel();
		Consumer<IResponse> update = response -> {
			if (response.hasFailed())
				send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
			else
				send(EMumbleClientCode.MUMBLE__PLAYERS__KICK__REQUEST_SUCCEED, kickedPlayer.getName(), channel.getName(), kickingPlayer.getName());
		};

		try {
			kickedPlayer.kick(kickingPlayer, update);
		} catch (PlayerNotAdministratorException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__KICK__KICKING_PLAYER_NOT_ADMIN, kickedPlayer.getName(), kickingPlayer.getName());
			return false;
		} catch (PlayerNotRegisteredInChannelException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__KICK__KICKED_PLAYER_NOT_REGISTERED_IN_CHANNEL, kickedPlayer.getName(), kickingPlayer.getName());
			return false;
		}
		return true;
	}
}
