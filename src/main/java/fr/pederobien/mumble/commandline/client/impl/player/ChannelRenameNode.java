package fr.pederobien.mumble.commandline.client.impl.player;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import fr.pederobien.mumble.client.common.interfaces.IResponse;
import fr.pederobien.mumble.client.player.interfaces.IChannel;
import fr.pederobien.mumble.client.player.interfaces.IPlayerMumbleServer;
import fr.pederobien.mumble.commandline.client.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.client.impl.MumbleClientNode;

public class ChannelRenameNode extends MumbleClientNode<IPlayerMumbleServer> {

	/**
	 * Creates a node in order to rename a channel.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelRenameNode(Supplier<IPlayerMumbleServer> server) {
		super(server, "rename", EMumbleClientCode.MUMBLE__CHANNEL__RENAME__EXPLANATION, s -> s != null && s.isJoined());
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		switch (args.length) {
		case 1:
			return filter(getServer().getChannels().stream().map(channel -> channel.getName()), args);
		case 2:
			Predicate<String> nameValid = name -> getServer().getChannels().get(name).isPresent();
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
			send(EMumbleClientCode.MUMBLE__CHANNEL__RENAME__NAME_IS_MISSING);
			return false;
		}

		Optional<IChannel> optOldChannel = getServer().getChannels().get(oldName);
		if (!optOldChannel.isPresent()) {
			send(EMumbleClientCode.MUMBLE__CHANNEL__RENAME__CHANNEL_NOT_FOUND, oldName);
			return false;
		}

		String newName;
		try {
			newName = args[1];
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__CHANNEL__RENAME__NEW_NAME_IS_MISSING, oldName);
			return false;
		}

		Optional<IChannel> optNewChannel = getServer().getChannels().get(newName);
		if (optNewChannel.isPresent()) {
			send(EMumbleClientCode.MUMBLE__CHANNEL__RENAME__CHANNEL_ALREADY_REGISTERED, oldName, newName);
			return false;
		}

		Consumer<IResponse> update = response -> {
			if (response.hasFailed())
				send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
			else
				send(EMumbleClientCode.MUMBLE__CHANNEL__RENAME__REQUEST_SUCCEED, oldName, newName);
		};
		optOldChannel.get().setName(newName, update);
		return true;
	}
}
