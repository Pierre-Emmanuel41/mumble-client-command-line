package fr.pederobien.mumble.commandline.impl;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import fr.pederobien.mumble.client.interfaces.IResponse;
import fr.pederobien.mumble.commandline.interfaces.IMumbleServerType;

public class ChannelRemoveChannelNode extends MumbleClientNode {

	/**
	 * Creates a node that removes a channel from the server.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelRemoveChannelNode(Supplier<IMumbleServerType> server) {
		super(server, "channel", EMumbleClientCode.MUMBLE__CHANNEL__REMOVE__CHANNEL__EXPLANATION, s -> s != null);

		setAvailable(() -> isAvailableAccordingServerProperties());
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		switch (args.length) {
		case 1:
			return asList(getMessage(EMumbleClientCode.MUMBLE__NAME__COMPLETION));
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
			send(EMumbleClientCode.MUMBLE__CHANNEL__REMOVE__CHANNEL__NAME_IS_MISSING, getServer().getName());
			return false;
		}

		if (!getServer().getChannels().get(name).isPresent()) {
			send(EMumbleClientCode.MUMBLE__CHANNEL__REMOVE__CHANNEL__CHANNEL_NOT_FOUND, name, getServer().getName());
			return false;
		}

		Consumer<IResponse> update = response -> {
			if (response.hasFailed())
				send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
			else
				send(EMumbleClientCode.MUMBLE__CHANNEL__REMOVE__CHANNEL__REQUEST_SUCCEED, name, getServer().getName());
		};
		getServer().getChannels().remove(name, update);
		return true;
	}
}
