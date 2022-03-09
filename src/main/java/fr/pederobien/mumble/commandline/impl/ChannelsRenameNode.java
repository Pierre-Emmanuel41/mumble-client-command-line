package fr.pederobien.mumble.commandline.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import fr.pederobien.mumble.client.interfaces.IChannel;
import fr.pederobien.mumble.client.interfaces.IMumbleServer;
import fr.pederobien.mumble.client.interfaces.IResponse;

public class ChannelsRenameNode extends MumbleClientNode {

	/**
	 * Creates a node in order to rename a channel.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelsRenameNode(Supplier<IMumbleServer> server) {
		super(server, "rename", EMumbleClientCode.MUMBLE__CHANNELS__RENAME__EXPLANATION, s -> s != null);
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		switch (args.length) {
		case 1:
			return filter(getServer().getChannelList().stream().map(channel -> channel.getName()), args);
		case 2:
			Predicate<String> nameValid = name -> getServer().getChannelList().getChannel(name).isPresent();
			return check(args[0], nameValid, asList(getMessage(EMumbleClientCode.MUMBLE__NAME__COMPLETION)));
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(String[] args) {
		String oldName;
		try {
			oldName = args[0];
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__CHANNELS__RENAME__NAME_IS_MISSING);
			return false;
		}

		Optional<IChannel> optOldChannel = getServer().getChannelList().getChannel(oldName);
		if (!optOldChannel.isPresent()) {
			send(EMumbleClientCode.MUMBLE__CHANNELS__RENAME__CHANNEL_NOT_FOUND, oldName);
			return false;
		}

		String newName;
		try {
			newName = args[1];
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__CHANNELS__RENAME__NEW_NAME_IS_MISSING, oldName);
			return false;
		}

		Optional<IChannel> optNewChannel = getServer().getChannelList().getChannel(newName);
		if (optNewChannel.isPresent()) {
			send(EMumbleClientCode.MUMBLE__CHANNELS__RENAME__CHANNEL_ALREADY_REGISTERED, oldName, newName);
			return false;
		}

		Consumer<IResponse> update = response -> {
			if (response.hasFailed())
				send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
			else
				send(EMumbleClientCode.MUMBLE__CHANNELS__RENAME__REQUEST_SUCCEED, oldName, newName);
		};
		optOldChannel.get().setName(newName, update);
		return true;
	}
}