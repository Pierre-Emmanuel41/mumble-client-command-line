package fr.pederobien.mumble.commandline.client.impl.external;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import fr.pederobien.commandtree.exceptions.BooleanParseException;
import fr.pederobien.mumble.client.common.interfaces.IResponse;
import fr.pederobien.mumble.client.external.interfaces.IExternalMumbleServer;
import fr.pederobien.mumble.commandline.client.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.client.impl.MumbleClientNode;

public class PlayersAdminNode extends MumbleClientNode<IExternalMumbleServer> {

	/**
	 * Creates a node to update the administrator status of a player.
	 * 
	 * @param server The server associated to this node.
	 */
	protected PlayersAdminNode(Supplier<IExternalMumbleServer> server) {
		super(server, "admin", EMumbleClientCode.MUMBLE__PLAYERS__ADMIN__EXPLANATION, s -> s != null);
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		switch (args.length) {
		case 1:
			return filter(getServer().getPlayers().stream().map(player -> player.getName()), args);
		case 2:
			Predicate<String> nameValid = name -> getServer().getPlayers().get(name).isPresent();
			return check(args[0], nameValid, asList(getMessage(EMumbleClientCode.MUMBLE__PLAYERS__ADMIN__COMPLETION)));
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(String[] args) {
		String name;
		try {
			name = args[0];
			if (!getServer().getPlayers().get(name).isPresent()) {
				send(EMumbleClientCode.MUMBLE__PLAYERS__ADMIN__PLAYER_NOT_FOUND, name);
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__ADMIN__NAME_IS_MISSING);
			return false;
		}

		boolean isAdmin;
		try {
			isAdmin = getBoolean(args[1]);
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__ADMIN__STATUS_IS_MISSING, name);
			return false;
		} catch (BooleanParseException e) {
			send(EMumbleClientCode.MUMBLE__PLAYERS__ADMIN__STATUS_BAD_FORMAT, name);
			return false;
		}

		Consumer<IResponse> update = response -> {
			if (response.hasFailed())
				send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
			else if (isAdmin)
				send(EMumbleClientCode.MUMBLE__PLAYERS__ADMIN__ADMIN_REQUEST_SUCCEED, name);
			else
				send(EMumbleClientCode.MUMBLE__PLAYERS__ADMIN__NOT_ADMIN_REQUEST_SUCCEED, name);
		};
		getServer().getPlayers().get(name).get().setAdmin(isAdmin, update);
		return true;
	}
}
