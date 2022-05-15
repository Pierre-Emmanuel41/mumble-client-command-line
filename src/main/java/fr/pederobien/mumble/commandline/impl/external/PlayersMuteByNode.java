package fr.pederobien.mumble.commandline.impl.external;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.pederobien.commandtree.exceptions.BooleanParseException;
import fr.pederobien.mumble.client.common.interfaces.IResponse;
import fr.pederobien.mumble.client.external.interfaces.IExternalMumbleServer;
import fr.pederobien.mumble.client.external.interfaces.IPlayer;
import fr.pederobien.mumble.commandline.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.impl.MumbleClientNode;

public class PlayersMuteByNode extends MumbleClientNode<IExternalMumbleServer> {

	/**
	 * Creates a node to mute or unmute a player for another player.
	 * 
	 * @param server The server associated to this node.
	 */
	protected PlayersMuteByNode(Supplier<IExternalMumbleServer> server) {
		super(server, "muteBy", EMumbleClientCode.MUMBLE__PLAYERS__MUTE_BY__EXPLANATION, s -> s != null);
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		switch (args.length) {
		case 1:
			return filter(getServer().getPlayers().stream().map(player -> player.getName()), args);
		case 2:
			Predicate<String> targetValid = name -> getServer().getPlayers().get(name).isPresent();
			Stream<IPlayer> sourcePlayers = getServer().getPlayers().stream().filter(player -> player.getName().equals(args[0]));
			return check(args[0], targetValid, sourcePlayers.map(player -> player.getName()).collect(Collectors.toList()));
		case 3:
			Predicate<String> sourceValid = name -> getServer().getPlayers().get(name).isPresent();
			return check(args[1], sourceValid, asList(getMessage(EMumbleClientCode.MUMBLE__PLAYERS__MUTE_BY__COMPLETION)));
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(String[] args) {
		IPlayer target;
		try {
			Optional<IPlayer> optPlayer = getServer().getPlayers().get(args[0]);
			if (!optPlayer.isPresent()) {
				send(EMumbleClientCode.MUMBLE__PLAYERS__MUTE_BY__TARGET_NOT_FOUND, args[0]);
				return false;
			}
			target = optPlayer.get();
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__MUTE_BY__TARGET_NAME_IS_MISSING);
			return false;
		}

		IPlayer source;
		try {
			Optional<IPlayer> optPlayer = getServer().getPlayers().get(args[1]);
			if (!optPlayer.isPresent()) {
				send(EMumbleClientCode.MUMBLE__PLAYERS__MUTE_BY__TARGET_NOT_FOUND, target.getName(), args[1]);
				return false;
			}
			source = optPlayer.get();
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__MUTE_BY__SOURCE_NAME_IS_MISSING, target.getName());
			return false;
		}

		boolean isMute;
		try {
			isMute = getBoolean(args[2]);
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__MUTE_BY__STATUS_IS_MISSING, target.getName(), source.getName());
			return false;
		} catch (BooleanParseException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__MUTE_BY__STATUS_BAD_FORMAT, target.getName(), source.getName());
			return false;
		}

		Consumer<IResponse> update = response -> {
			if (response.hasFailed())
				send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
			else if (isMute)
				send(EMumbleClientCode.MUMBLE__PLAYERS__MUTE_BY__MUTE_REQUEST_SUCCEED, target.getName(), source.getName());
			else
				send(EMumbleClientCode.MUMBLE__PLAYERS__MUTE_BY__UNMUTE_REQUEST_SUCCEED, target.getName(), source.getName());
		};
		target.setMuteBy(source, isMute, update);
		return true;
	}
}
