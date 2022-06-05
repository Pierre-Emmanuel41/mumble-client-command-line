package fr.pederobien.mumble.commandline.client.impl.player;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import fr.pederobien.mumble.client.common.interfaces.IResponse;
import fr.pederobien.mumble.client.player.interfaces.IPlayerMumbleServer;
import fr.pederobien.mumble.commandline.client.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.client.impl.MumbleClientNode;

public class ChannelRemoveNode extends MumbleClientNode<IPlayerMumbleServer> {

	/**
	 * Creates a node that removes a channel from the server.
	 * 
	 * @param server The server associated to this node.
	 */
	protected ChannelRemoveNode(Supplier<IPlayerMumbleServer> server) {
		super(server, "remove", EMumbleClientCode.MUMBLE__CHANNEL__REMOVE__CHANNEL__EXPLANATION, s -> s != null && s.isJoined());
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
