package fr.pederobien.mumble.commandline.client.impl.player;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Supplier;

import fr.pederobien.commandtree.exceptions.BooleanParseException;
import fr.pederobien.messenger.interfaces.IResponse;
import fr.pederobien.mumble.client.player.interfaces.IChannel;
import fr.pederobien.mumble.client.player.interfaces.IPlayerMumbleServer;
import fr.pederobien.mumble.commandline.client.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.client.impl.MumbleClientNode;

public class SetDeafenNode extends MumbleClientNode<IPlayerMumbleServer> {

	/**
	 * Creates a node in order to modify the deafen status of the main player.
	 * 
	 * @param server The server associated to this node.
	 */
	protected SetDeafenNode(Supplier<IPlayerMumbleServer> server) {
		super(server, "deafen", EMumbleClientCode.MUMBLE__SET__DEAFEN__EXPLANATION, s -> s != null && s.isJoined());
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		IChannel channel = getServer().getMainPlayer().getChannel();
		if (channel == null)
			return emptyList();

		switch (args.length) {
		case 1:
			return filter(asList("true", "false").stream(), args);
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(String[] args) {
		IChannel channel = getServer().getMainPlayer().getChannel();
		if (channel == null) {
			send(EMumbleClientCode.MUMBLE__SET__DEAFEN__PLAYER_NOT_REGISTERED_IN_CHANNEL);
			return true;
		}

		String first;
		try {
			first = args[0];
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__SET__DEAFEN__DEAFEN_STATUS_IS_MISSING, getServer().getMainPlayer().getName());
			return false;
		}

		AtomicBoolean isMute = new AtomicBoolean(false);
		try {
			isMute.set(getBoolean(first));
			Consumer<IResponse> update = response -> {
				if (response.hasFailed())
					send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
				else if (isMute.get())
					send(EMumbleClientCode.MUMBLE__SET__DEAFEN__DEAFENED_REQUEST_SUCCEED, getServer().getMainPlayer().getName());
				else
					send(EMumbleClientCode.MUMBLE__SET__DEAFEN__UNDEAFENED_REQUEST_SUCCEED, getServer().getMainPlayer().getName());
			};
			getServer().getMainPlayer().setDeafen(isMute.get(), update);
		} catch (BooleanParseException e) {
			send(EMumbleClientCode.MUMBLE__SET__DEAFEN__DEAFEN_STATUS_BAD_FORMAT, getServer().getMainPlayer().getName());
			return false;
		}
		return true;
	}

}
