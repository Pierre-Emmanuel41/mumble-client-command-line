package fr.pederobien.mumble.commandline.client.impl.player;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import fr.pederobien.mumble.client.common.interfaces.IResponse;
import fr.pederobien.mumble.client.player.exceptions.PlayerNotOnlineException;
import fr.pederobien.mumble.client.player.interfaces.IChannel;
import fr.pederobien.mumble.client.player.interfaces.IPlayerMumbleServer;
import fr.pederobien.mumble.commandline.client.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.client.impl.MumbleClientNode;

public class ChannelJoinNode extends MumbleClientNode<IPlayerMumbleServer> {

	/**
	 * Creates a node in order to join a channel.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelJoinNode(Supplier<IPlayerMumbleServer> server) {
		super(server, "join", EMumbleClientCode.MUMBLE__CHANNEL__JOIN__EXPLANATION, s -> s != null && s.isJoined());
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		switch (args.length) {
		case 1:
			return filter(getServer().getChannels().stream().map(channel -> channel.getName()), args);
		default:
			return emptyList();
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

		Consumer<IResponse> update = response -> {
			if (response.hasFailed())
				send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
			else
				send(EMumbleClientCode.MUMBLE__CHANNEL__ADD__PLAYERS__ONE_PLAYER_ADDED_REQUEST_SUCCEED, getServer().getMainPlayer().getName(), channel.getName());
		};

		try {
			channel.getPlayers().join(update);
		} catch (PlayerNotOnlineException e) {
			send(EMumbleClientCode.MUMBLE__CHANNEL__JOIN__PLAYER_NOT_ONLINE, channel.getName());
			return false;
		}
		return true;
	}
}
