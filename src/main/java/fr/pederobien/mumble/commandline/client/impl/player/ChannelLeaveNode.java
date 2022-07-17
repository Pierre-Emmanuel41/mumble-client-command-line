package fr.pederobien.mumble.commandline.client.impl.player;

import java.util.function.Consumer;
import java.util.function.Supplier;

import fr.pederobien.messenger.interfaces.IResponse;
import fr.pederobien.mumble.client.player.exceptions.PlayerNotOnlineException;
import fr.pederobien.mumble.client.player.interfaces.IPlayerMumbleServer;
import fr.pederobien.mumble.commandline.client.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.client.impl.MumbleClientNode;

public class ChannelLeaveNode extends MumbleClientNode<IPlayerMumbleServer> {

	/**
	 * Creates a node in order to leave a channel.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelLeaveNode(Supplier<IPlayerMumbleServer> server) {
		super(server, "leave", EMumbleClientCode.MUMBLE__CHANNEL__LEAVE__EXPLANATION, s -> s != null && s.isJoined());
	}

	@Override
	public boolean onCommand(String[] args) {
		if (getServer().getMainPlayer().getChannel() == null) {
			send(EMumbleClientCode.MUMBLE__CHANNEL__LEAVE__PLAYER_NOT_REGISTERED_IN_CHANNEL);
			return false;
		}

		String channelName = getServer().getMainPlayer().getChannel().getName();
		Consumer<IResponse> update = response -> {
			if (response.hasFailed())
				send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
			else
				send(EMumbleClientCode.MUMBLE__CHANNEL__REMOVE__PLAYERS__ONE_PLAYER_REMOVED_REQUEST_SUCCEED, getServer().getMainPlayer().getName(), channelName);
		};

		try {
			getServer().getMainPlayer().getChannel().getPlayers().leave(update);
		} catch (PlayerNotOnlineException e) {
			send(EMumbleClientCode.MUMBLE__CHANNEL__LEAVE__PLAYER_NOT_ONLINE, channelName);
			return false;
		}
		return true;
	}
}
