package fr.pederobien.mumble.commandline.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import fr.pederobien.mumble.client.common.interfaces.IResponse;
import fr.pederobien.mumble.client.external.interfaces.IChannel;
import fr.pederobien.mumble.client.external.interfaces.IPlayer;
import fr.pederobien.mumble.commandline.interfaces.IMumbleServerType;

public class ChannelAddPlayersNode extends MumbleClientNode {
	private int current;

	/**
	 * Creates a node in order to add players in a channel.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelAddPlayersNode(Supplier<IMumbleServerType> server) {
		super(server, "players", EMumbleClientCode.MUMBLE__CHANNEL__ADD__PLAYERS__EXPLANATION, s -> s != null);

		setAvailable(() -> isAvailableAccordingServerProperties());
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
				send(EMumbleClientCode.MUMBLE__CHANNEL__ADD__PLAYERS__CHANNEL_NOT_FOUND, args[0]);
				return false;
			}
			channel = optChannel.get();
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__CHANNEL__ADD__PLAYERS__CHANNEL_NAME_IS_MISSING);
			return false;
		}

		List<IPlayer> players = new ArrayList<IPlayer>();
		for (String name : extract(args, 1)) {
			Optional<IPlayer> optPlayer = getServer().getPlayers().get(name);
			if (!optPlayer.isPresent()) {
				send(EMumbleClientCode.MUMBLE__CHANNEL__ADD__PLAYERS__PLAYER_NOT_FOUND, name, channel.getName());
				return false;
			}

			players.add(optPlayer.get());
		}

		if (players.isEmpty()) {
			send(EMumbleClientCode.MUMBLE__CHANNEL__ADD__PLAYERS__NO_PLAYER_ADDED, channel.getName());
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
						send(EMumbleClientCode.MUMBLE__CHANNEL__ADD__PLAYERS__ONE_PLAYER_ADDED_REQUEST_SUCCEED, playerNames, channel.getName());
						break;
					default:
						send(EMumbleClientCode.MUMBLE__CHANNEL__ADD__PLAYERS__SEVERAL_PLAYERS_ADDED_REQUEST_SUCCEED, playerNames, channel.getName());
						break;
					}
				}
			}
		};

		for (IPlayer player : players)
			channel.getPlayers().add(player, update);
		return true;
	}
}
