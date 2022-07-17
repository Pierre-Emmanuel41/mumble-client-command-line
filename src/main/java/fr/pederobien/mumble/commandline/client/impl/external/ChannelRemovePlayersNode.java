package fr.pederobien.mumble.commandline.client.impl.external;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import fr.pederobien.messenger.interfaces.IResponse;
import fr.pederobien.mumble.client.external.interfaces.IChannel;
import fr.pederobien.mumble.client.external.interfaces.IExternalMumbleServer;
import fr.pederobien.mumble.client.external.interfaces.IPlayer;
import fr.pederobien.mumble.commandline.client.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.client.impl.MumbleClientNode;

public class ChannelRemovePlayersNode extends MumbleClientNode<IExternalMumbleServer> {
	private int current;

	/**
	 * Creates a node that removes players from a channel.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelRemovePlayersNode(Supplier<IExternalMumbleServer> server) {
		super(server, "players", EMumbleClientCode.MUMBLE__CHANNEL__REMOVE__PLAYERS__EXPLANATION, s -> s != null);
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		switch (args.length) {
		case 1:
			return filter(getServer().getChannels().stream().map(channel -> channel.getName()), args);
		case 2:
			Predicate<String> channelNameValid = name -> getServer().getChannels().get(name).isPresent();
			List<String> list = filter(getServer().getPlayers().stream().map(player -> player.getName()), args);
			return check(args[0], channelNameValid, list);
		default:
			Predicate<String> playerNameValid = name -> getServer().getPlayers().get(name).isPresent();
			List<String> alreadyMentionned = asList(extract(args, 1));
			List<String> filter = filter(getServer().getPlayers().stream().map(player -> player.getName()).filter(name -> !alreadyMentionned.contains(name)), args);
			return check(args[args.length - 2], playerNameValid, filter);
		}
	}

	@Override
	public boolean onCommand(String[] args) {
		IChannel channel;
		try {
			Optional<IChannel> optChannel = getServer().getChannels().get(args[0]);
			if (!optChannel.isPresent()) {
				send(EMumbleClientCode.MUMBLE__CHANNEL__REMOVE__PLAYERS__CHANNEL_NOT_FOUND, args[0]);
				return false;
			}
			channel = optChannel.get();
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__CHANNEL__REMOVE__PLAYERS__CHANNEL_NAME_IS_MISSING);
			return false;
		}

		List<IPlayer> players = new ArrayList<IPlayer>();
		for (String name : extract(args, 1)) {
			Optional<IPlayer> optPlayer = getServer().getPlayers().get(name);
			if (!optPlayer.isPresent()) {
				send(EMumbleClientCode.MUMBLE__CHANNEL__REMOVE__PLAYERS__PLAYER_NOT_FOUND, name, channel.getName());
				return false;
			}

			players.add(optPlayer.get());
		}

		if (players.isEmpty()) {
			send(EMumbleClientCode.MUMBLE__CHANNEL__REMOVE__PLAYERS__NO_PLAYER_REMOVED, channel.getName());
			return true;
		}

		String playerNames = concat(extract(args, 1));
		current = 0;
		Consumer<IResponse> update = response -> {
			if (response.hasFailed())
				send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
			else {
				current++;
				if (current == players.size()) {
					switch (players.size()) {
					case 1:
						send(EMumbleClientCode.MUMBLE__CHANNEL__REMOVE__PLAYERS__ONE_PLAYER_REMOVED_REQUEST_SUCCEED, playerNames, channel.getName());
						break;
					default:
						send(EMumbleClientCode.MUMBLE__CHANNEL__REMOVE__PLAYERS__SEVERAL_PLAYERS_REMOVED_REQUEST_SUCCEED, playerNames, channel.getName());
						break;
					}
				}
			}
		};

		for (IPlayer player : players)
			channel.getPlayers().remove(player, update);
		return true;
	}
}
