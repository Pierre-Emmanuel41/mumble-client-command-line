package fr.pederobien.mumble.commandline.client.impl.player;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import fr.pederobien.messenger.interfaces.IResponse;
import fr.pederobien.mumble.client.player.interfaces.IPlayerMumbleServer;
import fr.pederobien.mumble.client.player.interfaces.ISoundModifier;
import fr.pederobien.mumble.commandline.client.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.client.impl.MumbleClientNode;

public class ChannelAddNode extends MumbleClientNode<IPlayerMumbleServer> {

	/**
	 * Creates a node in order to add a channel on the server.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelAddNode(Supplier<IPlayerMumbleServer> server) {
		super(server, "add", EMumbleClientCode.MUMBLE__CHANNEL__ADD__EXPLANATION, s -> s != null && s.isJoined());
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		switch (args.length) {
		case 1:
			return asList(getMessage(EMumbleClientCode.MUMBLE__NAME__COMPLETION));

		case 2:
			Predicate<String> nameValid = name -> !getServer().getChannels().get(name).isPresent();
			return check(args[0], nameValid, getServer().getSoundModifiers().stream().map(soundModifier -> soundModifier.getName()).collect(Collectors.toList()));
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(String[] args) {
		String name;
		try {
			name = args[0];
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__CHANNEL__ADD__CHANNEL__NAME_IS_MISSING, getServer().getName());
			return false;
		}

		if (getServer().getChannels().get(name).isPresent()) {
			send(EMumbleClientCode.MUMBLE__CHANNEL__ADD__CHANNEL__CHANNEL_ALREADY_REGISTERED, name, getServer().getName());
			return false;
		}

		ISoundModifier soundModifier;
		try {
			Optional<ISoundModifier> optModifier = getServer().getSoundModifiers().get(args[1]);
			if (!optModifier.isPresent()) {
				send(EMumbleClientCode.MUMBLE__CHANNEL__ADD__CHANNEL__SOUND_MODIFIER_NOT_FOUND, name, getServer().getName(), args[1]);
				return false;
			}
			soundModifier = optModifier.get();
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__CHANNEL__ADD__CHANNEL__SOUND_MODIFIER_IS_MISSING, getServer().getName(), name);
			return false;
		}

		Consumer<IResponse> update = response -> {
			if (response.hasFailed())
				send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
			else
				send(EMumbleClientCode.MUMBLE__CHANNEL__ADD__CHANNEL__REQUEST_SUCCEED, name, soundModifier.getName(), getServer().getName());
		};
		getServer().getChannels().add(name, soundModifier, update);
		return true;
	}
}
