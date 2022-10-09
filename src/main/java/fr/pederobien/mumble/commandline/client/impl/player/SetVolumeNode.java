package fr.pederobien.mumble.commandline.client.impl.player;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import fr.pederobien.mumble.client.player.interfaces.IPlayer;
import fr.pederobien.mumble.client.player.interfaces.IPlayerMumbleServer;
import fr.pederobien.mumble.commandline.client.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.client.impl.MumbleClientNode;

public class SetVolumeNode extends MumbleClientNode<IPlayerMumbleServer> {

	/**
	 * Set the sound volume of a player.
	 * 
	 * @param server The server associated to this node.
	 */
	protected SetVolumeNode(Supplier<IPlayerMumbleServer> server) {
		super(server, "volume", EMumbleClientCode.MUMBLE__SET__VOLUME__EXPLANATION, s -> s != null && s.isJoined());
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		switch (args.length) {
		case 1:
			return filter(getServer().getPlayers().stream().map(player -> player.getName()), args);
		case 2:
			Predicate<String> isNameValid = name -> getServer().getPlayers().get(name).isPresent();
			return check(args[0], isNameValid, asList(getMessage(EMumbleClientCode.MUMBLE__VOLUME_COMPLETION)));
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
				send(EMumbleClientCode.MUMBLE__SET__VOLUME__PLAYER_NOT_FOUND, args[0]);
				return false;
			}

			player = optPlayer.get();
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__SET__VOLUME__NAME_IS_MISSING);
			return false;
		}

		float volume;
		try {
			volume = Float.parseFloat(args[1]);
			if (volume < 0 || 200 < volume) {
				send(EMumbleClientCode.MUMBLE__SET__VOLUME__VOLUME_OUT_OF_RANGE, player.getName());
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__SET__VOLUME__VOLUME_IS_MISSING, player.getName());
			return false;
		} catch (NumberFormatException e) {
			send(EMumbleClientCode.MUMBLE__SET__VOLUME__VOLUME_BAD_FORMAT, player.getName());
			return false;
		}

		player.setVolume((float) (volume / 100.0));
		send(EMumbleClientCode.MUMBLE__SET__VOLUME__VOLUME_UPDATED, player.getName(), volume);
		return true;
	}
}
