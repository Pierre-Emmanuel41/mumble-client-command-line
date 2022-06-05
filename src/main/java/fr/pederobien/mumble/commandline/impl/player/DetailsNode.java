package fr.pederobien.mumble.commandline.impl.player;

import java.text.DecimalFormat;
import java.util.StringJoiner;
import java.util.function.Supplier;

import fr.pederobien.mumble.client.player.impl.RangeParameter;
import fr.pederobien.mumble.client.player.interfaces.IChannel;
import fr.pederobien.mumble.client.player.interfaces.IParameter;
import fr.pederobien.mumble.client.player.interfaces.IPlayer;
import fr.pederobien.mumble.client.player.interfaces.IPlayerMumbleServer;
import fr.pederobien.mumble.client.player.interfaces.IRangeParameter;
import fr.pederobien.mumble.client.player.interfaces.ISoundModifier;
import fr.pederobien.mumble.commandline.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.impl.MumbleClientNode;

public class DetailsNode extends MumbleClientNode<IPlayerMumbleServer> {

	/**
	 * Creates a node in order to display the server configuration.
	 * 
	 * @param server The server associated to this node.
	 */
	protected DetailsNode(Supplier<IPlayerMumbleServer> server) {
		super(server, "details", EMumbleClientCode.MUMBLE__DETAILS__EXPLANATION, s -> s != null && s.isJoined());
	}

	@Override
	public boolean onCommand(String[] args) {
		String tabulations = "\t";
		StringJoiner serverJoiner = new StringJoiner("\n");
		int counter, size;

		// Server's name
		String serverName = getMessage(EMumbleClientCode.MUMBLE__DETAILS__SERVER_NAME, getServer().getName());
		serverJoiner.add(serverName);

		// Server's IP address
		String ipAddress = getServer().getAddress().getAddress().getHostAddress();
		int port = getServer().getAddress().getPort();
		String serverAddress = getMessage(EMumbleClientCode.MUMBLE__DETAILS__SERVER_IP_ADDRESS, ipAddress, port);
		serverJoiner.add(serverAddress);

		// Server's reachable status
		EMumbleClientCode reachableCode;
		if (getServer().isReachable())
			reachableCode = EMumbleClientCode.MUMBLE__DETAILS__SERVER_REACHABLE;
		else
			reachableCode = EMumbleClientCode.MUMBLE__DETAILS__SERVER_NOT_REACHABLE;

		String serverReachable = getMessage(EMumbleClientCode.MUMBLE__DETAILS__SERVER_REACHABLE_STATUS, getMessage(reachableCode));
		serverJoiner.add(serverReachable);

		if (getServer().isReachable()) {
			serverJoiner.add("");

			// Server's sound modifiers
			String soundModifiers = getMessage(EMumbleClientCode.MUMBLE__DETAILS__SOUND_MODIFIERS);
			serverJoiner.add(soundModifiers);

			counter = 0;
			size = getServer().getSoundModifiers().toList().size();
			for (ISoundModifier soundModifier : getServer().getSoundModifiers()) {
				// Sound modifier's name
				String modifierName = getMessage(EMumbleClientCode.MUMBLE__DETAILS__SOUND_MODIFIER_NAME, soundModifier.getName());
				serverJoiner.add(tabulations.concat(modifierName));

				// Sound modifier's parameters
				String parameters = getMessage(EMumbleClientCode.MUMBLE__DETAILS__SOUND_MODIFIER_PARAMETERS);
				serverJoiner.add(tabulations.concat(parameters));

				String parameterTabulations = tabulations.concat(tabulations);
				int parameterCounter = 0;
				int parameterSize = soundModifier.getParameters().toList().size();
				for (IParameter<?> parameter : soundModifier.getParameters()) {
					// Parameter's name
					String parameterName = getMessage(EMumbleClientCode.MUMBLE__DETAILS__PARAMETER_NAME, parameter.getName());
					serverJoiner.add(parameterTabulations.concat(parameterName));

					// Parameter's value
					String parameterValue = getMessage(EMumbleClientCode.MUMBLE__DETAILS__PARAMETER_VALUE, parameter.getValue());
					serverJoiner.add(parameterTabulations.concat(parameterValue));

					// Parameter's default value
					String parameterDefaultValue = getMessage(EMumbleClientCode.MUMBLE__DETAILS__PARAMETER_DEFAULT_VALUE, parameter.getDefaultValue());
					serverJoiner.add(parameterTabulations.concat(parameterDefaultValue));

					if (parameter instanceof IRangeParameter<?>) {
						// Parameter's minimum value
						String parameterMinValue = getMessage(EMumbleClientCode.MUMBLE__DETAILS__PARAMETER_MINIMUM_VALUE, ((RangeParameter<?>) parameter).getMin());
						serverJoiner.add(parameterTabulations.concat(parameterMinValue));

						// Parameter's maximum value
						String parameterMaxValue = getMessage(EMumbleClientCode.MUMBLE__DETAILS__PARAMETER_MAXIMUM_VALUE, ((RangeParameter<?>) parameter).getMax());
						serverJoiner.add(parameterTabulations.concat(parameterMaxValue));
					}

					parameterCounter++;
					if (parameterCounter < parameterSize)
						serverJoiner.add("");
				}

				counter++;
				if (counter < size)
					serverJoiner.add("");
			}

			serverJoiner.add("");

			// Server's main player
			serverJoiner.add(getMessage(EMumbleClientCode.MUMBLE__DETAILS__MAIN_PLAYER));

			// Player's name
			String playerName = getMessage(EMumbleClientCode.MUMBLE__DETAILS__PLAYER_NAME, getServer().getMainPlayer().getName());
			serverJoiner.add(tabulations.concat(playerName));

			String playerTabulations = tabulations.concat(tabulations);

			// Player's identifier
			String playerIdentifier = getMessage(EMumbleClientCode.MUMBLE__DETAILS__PLAYER_IDENTIFIER, getServer().getMainPlayer().getIdentifier());
			serverJoiner.add(playerTabulations.concat(playerIdentifier));

			// Player's online status
			EMumbleClientCode onlineCode;
			if (getServer().getMainPlayer().isOnline())
				onlineCode = EMumbleClientCode.MUMBLE__DETAILS__PLAYER_ONLINE;
			else
				onlineCode = EMumbleClientCode.MUMBLE__DETAILS__PLAYER_OFFLINE;

			String playerOnline = getMessage(EMumbleClientCode.MUMBLE__DETAILS__PLAYER_ONLINE_STATUS, getMessage(onlineCode));
			serverJoiner.add(playerTabulations.concat(playerOnline));

			if (getServer().getMainPlayer().isOnline()) {
				// Player's game address
				String ipGameAddress = getServer().getMainPlayer().getGameAddress().getAddress().getHostAddress();
				int gamePort = getServer().getMainPlayer().getGameAddress().getPort();
				String playerGameAddress = getMessage(EMumbleClientCode.MUMBLE__DETAILS__PLAYER_GAME_ADDRESS, ipGameAddress, gamePort);
				serverJoiner.add(playerTabulations.concat(playerGameAddress));

				// Player's admin status
				EMumbleClientCode adminCode;
				if (getServer().getMainPlayer().isAdmin())
					adminCode = EMumbleClientCode.MUMBLE__DETAILS__PLAYER_ADMIN;
				else
					adminCode = EMumbleClientCode.MUMBLE__DETAILS__PLAYER_NOT_ADMIN;

				String playerAdmin = getMessage(EMumbleClientCode.MUMBLE__DETAILS__PLAYER_ADMIN_STATUS, getMessage(adminCode));
				serverJoiner.add(playerTabulations.concat(playerAdmin));

				// Player's mute status
				EMumbleClientCode muteCode;
				if (getServer().getMainPlayer().isMute())
					muteCode = EMumbleClientCode.MUMBLE__DETAILS__PLAYER_MUTE;
				else
					muteCode = EMumbleClientCode.MUMBLE__DETAILS__PLAYER_NOT_MUTE;

				String playerMute = getMessage(EMumbleClientCode.MUMBLE__DETAILS__PLAYER_MUTE_STATUS, getMessage(muteCode));
				serverJoiner.add(playerTabulations.concat(playerMute));

				// Player's deafen status
				EMumbleClientCode deafenCode;
				if (getServer().getMainPlayer().isDeafen())
					deafenCode = EMumbleClientCode.MUMBLE__DETAILS__PLAYER_DEAFEN;
				else
					deafenCode = EMumbleClientCode.MUMBLE__DETAILS__PLAYER_NOT_DEAFEN;

				String playerDeafen = getMessage(EMumbleClientCode.MUMBLE__DETAILS__PLAYER_DEAFEN_STATUS, getMessage(deafenCode));
				serverJoiner.add(playerTabulations.concat(playerDeafen));

				// Player's position
				DecimalFormat format = new DecimalFormat("#.####");
				String x = format.format(getServer().getMainPlayer().getPosition().getX());
				String y = format.format(getServer().getMainPlayer().getPosition().getY());
				String z = format.format(getServer().getMainPlayer().getPosition().getZ());
				String yaw = format.format(getServer().getMainPlayer().getPosition().getYaw());
				String pitch = format.format(getServer().getMainPlayer().getPosition().getPitch());
				String playerPosition = getMessage(EMumbleClientCode.MUMBLE__DETAILS__PLAYER_POSITION, x, y, z, yaw, pitch);
				serverJoiner.add(playerTabulations.concat(playerPosition));
			}

			serverJoiner.add("");

			// Server's channel
			String channels = getMessage(EMumbleClientCode.MUMBLE__DETAILS__CHANNELS);
			serverJoiner.add(channels);

			counter = 0;
			size = getServer().getChannels().toList().size();
			for (IChannel channel : getServer().getChannels()) {
				// Channel's name
				String channelName = getMessage(EMumbleClientCode.MUMBLE__DETAILS__CHANNEL_NAME, channel.getName());
				serverJoiner.add(tabulations.concat(channelName));

				// Channel's sound modifier
				String soundModifier = getMessage(EMumbleClientCode.MUMBLE__DETAILS__CHANNEL_SOUND_MODIFIER);
				serverJoiner.add(tabulations.concat(soundModifier));

				String modifierTabulations = tabulations.concat(tabulations);

				// Sound modifier's name
				String modifierName = getMessage(EMumbleClientCode.MUMBLE__DETAILS__SOUND_MODIFIER_NAME, channel.getSoundModifier().getName());
				serverJoiner.add(modifierTabulations.concat(modifierName));

				// Sound modifier's parameters
				String parameters = getMessage(EMumbleClientCode.MUMBLE__DETAILS__SOUND_MODIFIER_PARAMETERS);
				serverJoiner.add(modifierTabulations.concat(parameters));

				String parameterTabulations = tabulations.concat(modifierTabulations);
				int parameterCounter = 0;
				int parameterSize = channel.getSoundModifier().getParameters().toList().size();
				for (IParameter<?> parameter : channel.getSoundModifier().getParameters()) {
					// Parameter's name
					String parameterName = getMessage(EMumbleClientCode.MUMBLE__DETAILS__PARAMETER_NAME, parameter.getName());
					serverJoiner.add(parameterTabulations.concat(parameterName));

					// Parameter's value
					String parameterValue = getMessage(EMumbleClientCode.MUMBLE__DETAILS__PARAMETER_VALUE, parameter.getValue());
					serverJoiner.add(parameterTabulations.concat(parameterValue));

					if (parameter instanceof IRangeParameter<?>) {
						// Parameter's minimum value
						String parameterMinValue = getMessage(EMumbleClientCode.MUMBLE__DETAILS__PARAMETER_MINIMUM_VALUE, ((RangeParameter<?>) parameter).getMin());
						serverJoiner.add(parameterTabulations.concat(parameterMinValue));

						// Parameter's maximum value
						String parameterMaxValue = getMessage(EMumbleClientCode.MUMBLE__DETAILS__PARAMETER_MAXIMUM_VALUE, ((RangeParameter<?>) parameter).getMax());
						serverJoiner.add(parameterTabulations.concat(parameterMaxValue));
					}

					parameterCounter++;
					if (parameterCounter < parameterSize)
						serverJoiner.add("");
				}

				// Server's players
				String channelPlayers = getMessage(EMumbleClientCode.MUMBLE__DETAILS__PLAYERS);
				serverJoiner.add(tabulations.concat(channelPlayers));

				for (IPlayer player : channel.getPlayers()) {
					// Player's name
					playerName = getMessage(EMumbleClientCode.MUMBLE__DETAILS__PLAYER_NAME, player.getName());
					serverJoiner.add(playerTabulations.concat(playerName));
				}

				counter++;
				if (counter < size)
					serverJoiner.add("");
			}
		}

		send(EMumbleClientCode.MUMBLE__DETAILS__SERVER, serverJoiner);
		return true;
	}
}
