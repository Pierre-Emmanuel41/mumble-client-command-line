package fr.pederobien.mumble.commandline.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import fr.pederobien.mumble.client.interfaces.IMumbleServer;
import fr.pederobien.mumble.client.interfaces.IResponse;
import fr.pederobien.mumble.client.interfaces.ISoundModifier;

public class ChannelsAddNode extends MumbleClientNode {

	/**
	 * Creates a node to add a channel to a server.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelsAddNode(Supplier<IMumbleServer> server) {
		super(server, "add", EMumbleClientCode.MUMBLE__CHANNELS__ADD__EXPLANATION, s -> s != null);
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		switch (args.length) {
		case 1:
			return asList(getMessage(EMumbleClientCode.MUMBLE__NAME__COMPLETION));

		case 2:
			Predicate<String> nameValid = name -> !getServer().getChannelList().getChannel(name).isPresent();
			return check(args[0], nameValid, getServer().getSoundModifierList().stream().map(soundModifier -> soundModifier.getName()).collect(Collectors.toList()));
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
			send(EMumbleClientCode.MUMBLE__CHANNELS__ADD__NAME_IS_MISSING, getServer().getName());
			return false;
		}

		if (getServer().getChannelList().getChannel(name).isPresent()) {
			send(EMumbleClientCode.MUMBLE__CHANNELS__ADD__CHANNEL_ALREADY_REGISTERED, name, getServer().getName());
			return false;
		}

		ISoundModifier soundModifier;
		try {
			Optional<ISoundModifier> optModifier = getServer().getSoundModifierList().get(args[1]);
			if (!optModifier.isPresent()) {
				send(EMumbleClientCode.MUMBLE__CHANNELS__ADD__SOUND_MODIFIER_NOT_FOUND, name, getServer().getName(), args[1]);
				return false;
			}
			soundModifier = optModifier.get();
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__CHANNELS__ADD__SOUND_MODIFIER_IS_MISSING, getServer().getName(), name);
			return false;
		}

		Consumer<IResponse> update = response -> {
			if (response.hasFailed())
				send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
			else
				send(EMumbleClientCode.MUMBLE__CHANNELS__ADD__REQUEST_SUCCEED, name, soundModifier.getName(), getServer().getName());
		};
		getServer().getChannelList().add(name, soundModifier, update);
		return true;
	}
}
