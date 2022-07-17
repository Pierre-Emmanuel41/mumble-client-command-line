package fr.pederobien.mumble.commandline.client.impl.player;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import fr.pederobien.messenger.interfaces.IResponse;
import fr.pederobien.mumble.client.player.exceptions.PlayerNotAdministratorException;
import fr.pederobien.mumble.client.player.exceptions.PlayerNotRegisteredInChannelException;
import fr.pederobien.mumble.client.player.interfaces.IChannel;
import fr.pederobien.mumble.client.player.interfaces.IPlayer;
import fr.pederobien.mumble.client.player.interfaces.IPlayerMumbleServer;
import fr.pederobien.mumble.commandline.client.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.client.impl.MumbleClientNode;

public class KickNode extends MumbleClientNode<IPlayerMumbleServer> {

	/**
	 * Creates a node in order to kick a player from a channel.
	 * 
	 * @param server The server associated to this node.
	 */
	protected KickNode(Supplier<IPlayerMumbleServer> server) {
		super(server, "kick", EMumbleClientCode.MUMBLE__PLAYERS__KICK__EXPLANATION, s -> s != null && s.isJoined());
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		switch (args.length) {
		case 1:
			return filter(getServer().getPlayers().stream().map(player -> player.getName()), args);
		case 2:
			Predicate<String> nameValid = name -> getServer().getPlayers().stream().filter(player -> player.getName().equals(name)).findFirst().isPresent();
			return check(args[0], nameValid, getServer().getPlayers().stream().map(player -> player.getName()).collect(Collectors.toList()));
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

		IChannel channel = kickedPlayer.getChannel();
		Consumer<IResponse> update = response -> {
			if (response.hasFailed())
				send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
			else
				send(EMumbleClientCode.MUMBLE__PLAYERS__KICK__REQUEST_SUCCEED, kickedPlayer.getName(), channel.getName(), getServer().getMainPlayer().getName());
		};

		try {
			kickedPlayer.kick(update);
		} catch (PlayerNotAdministratorException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__KICK__KICKING_PLAYER_NOT_ADMIN, kickedPlayer.getName(), getServer().getMainPlayer().getName());
			return false;
		} catch (PlayerNotRegisteredInChannelException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__KICK__KICKED_PLAYER_NOT_REGISTERED_IN_CHANNEL, kickedPlayer.getName(), getServer().getMainPlayer().getName());
			return false;
		}
		return true;
	}
}
