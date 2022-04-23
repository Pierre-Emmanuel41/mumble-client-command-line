package fr.pederobien.mumble.commandline.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import fr.pederobien.mumble.client.interfaces.IPlayer;
import fr.pederobien.mumble.client.interfaces.IResponse;
import fr.pederobien.mumble.commandline.interfaces.IMumbleServerType;

public class PlayersPositionNode extends MumbleClientNode {

	/**
	 * Creates a node in order to update the position of a player.
	 * 
	 * @param server The server associated to this node.
	 */
	protected PlayersPositionNode(Supplier<IMumbleServerType> server) {
		super(server, "position", EMumbleClientCode.MUMBLE__PLAYERS__POSITION__EXPLANATION, s -> s != null);

		setAvailable(() -> isAvailableAccordingServerProperties());
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		switch (args.length) {
		case 1:
			return filter(getServer().getPlayers().stream().map(player -> player.getName()), args);
		case 2:
			Predicate<String> nameValid = name -> getServer().getPlayers().get(name).isPresent();
			return check(args[0], nameValid, asList(getMessage(EMumbleClientCode.MUMBLE__PLAYERS__POSITION__X_COMPLETION)));
		case 3:
			return check(args[1], x -> isNotStrictDouble(x), asList(getMessage(EMumbleClientCode.MUMBLE__PLAYERS__POSITION__Y_COMPLETION)));
		case 4:
			return check(args[2], x -> isNotStrictDouble(x), asList(getMessage(EMumbleClientCode.MUMBLE__PLAYERS__POSITION__Z_COMPLETION)));
		case 5:
			return check(args[3], x -> isNotStrictDouble(x), asList(getMessage(EMumbleClientCode.MUMBLE__PLAYERS__POSITION__YAW_COMPLETION)));
		case 6:
			return check(args[4], x -> isNotStrictDouble(x), asList(getMessage(EMumbleClientCode.MUMBLE__PLAYERS__POSITION__PITCH_COMPLETION)));
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(String[] args) {
		IPlayer player;
		try {
			Optional<IPlayer> optPlayer = getServer().getPlayers().get(args[0]);
			if (!optPlayer.isPresent()) {
				send(EMumbleClientCode.MUMBLE__PLAYERS__POSITION__PLAYER_NOT_FOUND, args[0]);
				return false;
			}

			player = optPlayer.get();
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__POSITION__PLAYER_NAME_IS_MISSING);
			return false;
		}

		double x;
		try {
			x = getDouble(args[1]);
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__POSITION__X_IS_MISSING, player.getName());
			return false;
		} catch (NumberFormatException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__POSITION__X_BAD_FORMAT, player.getName());
			return false;
		}

		double y;
		try {
			y = getDouble(args[2]);
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__POSITION__Y_IS_MISSING, player.getName());
			return false;
		} catch (NumberFormatException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__POSITION__Y_BAD_FORMAT, player.getName());
			return false;
		}

		double z;
		try {
			z = getDouble(args[3]);
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__POSITION__Z_IS_MISSING, player.getName());
			return false;
		} catch (NumberFormatException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__POSITION__Z_BAD_FORMAT, player.getName());
			return false;
		}

		double yaw;
		try {
			yaw = getDouble(args[4]);
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__POSITION__YAW_IS_MISSING, player.getName());
			return false;
		} catch (NumberFormatException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__POSITION__YAW_BAD_FORMAT, player.getName());
			return false;
		}

		double pitch;
		try {
			pitch = getDouble(args[5]);
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__POSITION__PITCH_IS_MISSING, player.getName());
			return false;
		} catch (NumberFormatException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__POSITION__PITCH_BAD_FORMAT, player.getName());
			return false;
		}

		Consumer<IResponse> update = response -> {
			if (response.hasFailed())
				send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
			else
				send(EMumbleClientCode.MUMBLE__PLAYERS__POSITION__REQUEST_SUCCEED, player.getName(), player.getPosition().getX(), player.getPosition().getY(),
						player.getPosition().getZ(), player.getPosition().getYaw(), player.getPosition().getPitch());
		};

		player.getPosition().update(x, y, z, yaw, pitch, update);
		return true;
	}
}
