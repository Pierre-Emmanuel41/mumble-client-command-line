package fr.pederobien.mumble.commandline.client.impl.player;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import fr.pederobien.commandtree.exceptions.BooleanParseException;
import fr.pederobien.messenger.interfaces.IResponse;
import fr.pederobien.mumble.client.player.interfaces.IChannel;
import fr.pederobien.mumble.client.player.interfaces.IPlayer;
import fr.pederobien.mumble.client.player.interfaces.IPlayerMumbleServer;
import fr.pederobien.mumble.commandline.client.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.client.impl.MumbleClientNode;

public class SetMuteNode extends MumbleClientNode<IPlayerMumbleServer> {

	/**
	 * Creates a node in order to modify the mute status of the main player and/or the mute status of players registered in a channel.
	 * 
	 * @param server The server associated to this node.
	 */
	protected SetMuteNode(Supplier<IPlayerMumbleServer> server) {
		super(server, "mute", EMumbleClientCode.MUMBLE__SET__MUTE__EXPLANATION, s -> s != null && s.isJoined());
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		IChannel channel = getServer().getMainPlayer().getChannel();
		if (channel == null)
			return emptyList();

		switch (args.length) {
		case 1:
			List<String> trueFalseList = asList("true", "false");
			trueFalseList.addAll(channel.getPlayers().stream().map(player -> player.getName()).collect(Collectors.toList()));
			return filter(trueFalseList.stream(), args);
		case 2:
			if (args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("false"))
				return emptyList();

			Predicate<String> nameValid = name -> getServer().getMainPlayer().getChannel().getPlayers().get(name).isPresent();
			return check(args[0], nameValid, asList("true", "false"));
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(String[] args) {
		IChannel channel = getServer().getMainPlayer().getChannel();
		if (channel == null) {
			send(EMumbleClientCode.MUMBLE__SET__MUTE__PLAYER_NOT_REGISTERED_IN_CHANNEL);
			return true;
		}

		String first;
		try {
			first = args[0];
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__SET__MUTE__PLAYER_NAME_OR_MUTE_STATUS_IS_MISSING);
			return false;
		}

		// First case: Command is "set mute <isMute>"
		AtomicBoolean isMute = new AtomicBoolean(false);
		try {
			isMute.set(getBoolean(first));
			Consumer<IResponse> update = response -> {
				if (response.hasFailed())
					send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
				else if (isMute.get())
					send(EMumbleClientCode.MUMBLE__SET__MUTE__MUTED_REQUEST_SUCCEED, getServer().getMainPlayer().getName());
				else
					send(EMumbleClientCode.MUMBLE__SET__MUTE__UNMUTED_REQUEST_SUCCEED, getServer().getMainPlayer().getName());
			};
			getServer().getMainPlayer().setMute(isMute.get(), update);
			return true;
		} catch (BooleanParseException e) {
			// do nothing
		}

		// Second case: Command is "set mute <player> <isMute>"
		Optional<IPlayer> optPlayer = getServer().getMainPlayer().getChannel().getPlayers().get(first);
		if (!optPlayer.isPresent()) {
			send(EMumbleClientCode.MUMBLE__SET__MUTE__PLAYER_NOT_REGISTERED_IN_SAME_CHANNEL, first, channel.getName());
			return false;
		}

		try {
			isMute.set(getBoolean(args[1]));
			Consumer<IResponse> update = response -> {
				if (response.hasFailed())
					send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
				else if (isMute.get())
					send(EMumbleClientCode.MUMBLE__SET__MUTE__PLAYER_MUTED_REQUEST_SUCCEED, optPlayer.get().getName(), getServer().getMainPlayer().getName());
				else
					send(EMumbleClientCode.MUMBLE__SET__MUTE__PLAYER_UNMUTED_REQUEST_SUCCEED, optPlayer.get().getName(), getServer().getMainPlayer().getName());
			};
			optPlayer.get().setMute(isMute.get(), update);
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__SET__MUTE__MUTE_STATUS_IS_MISSING, optPlayer.get().getName());
			return false;
		} catch (BooleanParseException e) {
			send(EMumbleClientCode.MUMBLE__SET__MUTE__MUTE_STATUS_BAD_FORMAT, args[1]);
			return false;
		}

		return true;
	}
}
