package fr.pederobien.mumble.commandline.impl;

import fr.pederobien.mumble.commandline.interfaces.ICode;

public enum EMumbleClientCode implements ICode {

	// Starting application ------------------------------------------------------
	MUMBLE__STARTING,

	// Stopping application ------------------------------------------------------
	MUMBLE__STOPPING,

	// Common codes --------------------------------------------------------------
	MUMBLE__NAME__COMPLETION,

	// Code for the IP address completion
	MUMBLE__ADDRESS_COMPLETION,

	// Code for the port number completion
	MUMBLE__PORT_COMPLETION,

	// Code when the server returns a fail code
	MUMBLE__REQUEST_FAILED,

	// Code when a node does not exist
	MUMBLE__NODE_NOT_FOUND,

	// Code when a node is not available
	MUMBLE__NODE_NOT_AVAILABLE,

	// Code for the "mumble" command ---------------------------------------------
	MUMBLE__ROOT__EXPLANATION,

	// Code for the "mumble connect" command -------------------------------------
	MUMBLE__CONNECT__EXPLANATION,

	// Code when the IP address is missing
	MUMBLE__CONNECT__ADDRESS_IS_MISSING,

	// Code when no IP address for the host could be found
	MUMBLE__CONNECT__ADDRESS_NOT_FOUND,

	// Code when the port number is missing
	MUMBLE__CONNECT__PORT_NUMBER_IS_MISSING,

	// Code when the port number has an invalid format
	MUMBLE__CONNECT__PORT_NUMBER_BAD_FORMAT,

	// Code when the port number has an invalid range
	MUMBLE__CONNECT__PORT_NUMBER_BAD_RANGE,

	// Code when trying to connect to the remote
	MUMBLE__CONNECT__ATTEMPTING_CONNECTION,

	// Code when the connection with the remote succeed
	MUMBLE__CONNECT__CONNECTION_COMPLETE,

	// Code when the connection with the remote failed
	MUMBLE__CONNECT__CONNECTION_ABORT,

	// Code for the "mumble disconnect" command ----------------------------------
	MUMBLE__DISCONNECT__EXPLANATION,

	// Code when the connection is aborted
	MUMBLE__DISCONNECT__CONNECTION_ABORTED,

	// Code for the "mumble channel" command ------------------------------------
	MUMBLE__CHANNEL__EXPLANATION,

	// Code for the "mumble channel add" command --------------------------------
	MUMBLE__CHANNEL__ADD__EXPLANATION,

	// Code for the "mumble channel add channels" command ------------------------
	MUMBLE__CHANNEL__ADD__CHANNEL__EXPLANATION,

	// Code when the channel name is missing
	MUMBLE__CHANNEL__ADD__CHANNEL__NAME_IS_MISSING,

	// Code when the channel is already registered
	MUMBLE__CHANNEL__ADD__CHANNEL__CHANNEL_ALREADY_REGISTERED,

	// Code when the sound modifier name is missing
	MUMBLE__CHANNEL__ADD__CHANNEL__SOUND_MODIFIER_IS_MISSING,

	// Code when the sound modifier does not exist
	MUMBLE__CHANNEL__ADD__CHANNEL__SOUND_MODIFIER_NOT_FOUND,

	// Code when the channel has been added on the remote
	MUMBLE__CHANNEL__ADD__CHANNEL__REQUEST_SUCCEED,

	// Code for the "mumble channel add players" command ------------------------
	MUMBLE__CHANNEL__ADD__PLAYERS__EXPLANATION,

	// Code when the channel name is missing
	MUMBLE__CHANNEL__ADD__PLAYERS__CHANNEL_NAME_IS_MISSING,

	// Code when the channel does not exist
	MUMBLE__CHANNEL__ADD__PLAYERS__CHANNEL_NOT_FOUND,

	// Code when the player does not exist
	MUMBLE__CHANNEL__ADD__PLAYERS__PLAYER_NOT_FOUND,

	// Code when no player has been added
	MUMBLE__CHANNEL__ADD__PLAYERS__NO_PLAYER_ADDED,

	// Code when one player has been added
	MUMBLE__CHANNEL__ADD__PLAYERS__ONE_PLAYER_ADDED_REQUEST_SUCCEED,

	// Code when several players has been added
	MUMBLE__CHANNEL__ADD__PLAYERS__SEVERAL_PLAYERS_ADDED_REQUEST_SUCCEED,

	// Code for the "mumble channel remove" command -----------------------------
	MUMBLE__CHANNEL__REMOVE__EXPLANATION,

	// Code for the "mumble channel remove channel" command ---------------------
	MUMBLE__CHANNEL__REMOVE__CHANNEL__EXPLANATION,

	// Code when the channel name is missing
	MUMBLE__CHANNEL__REMOVE__CHANNEL__NAME_IS_MISSING,

	// Code when the channel name is missing
	MUMBLE__CHANNEL__REMOVE__CHANNEL__CHANNEL_NOT_FOUND,

	// Code when the channel has been removed from the remote
	MUMBLE__CHANNEL__REMOVE__CHANNEL__REQUEST_SUCCEED,

	// Code for the "mumble channel remove channels" command ---------------------
	MUMBLE__CHANNEL__REMOVE__PLAYERS__EXPLANATION,

	// Code when the channel name is missing
	MUMBLE__CHANNEL__REMOVE__PLAYERS__CHANNEL_NAME_IS_MISSING,

	// Code when the channel does not exist
	MUMBLE__CHANNEL__REMOVE__PLAYERS__CHANNEL_NOT_FOUND,

	// Code when the player does not exist
	MUMBLE__CHANNEL__REMOVE__PLAYERS__PLAYER_NOT_FOUND,

	// Code when no player has been removed
	MUMBLE__CHANNEL__REMOVE__PLAYERS__NO_PLAYER_REMOVED,

	// Code when one player has been removed
	MUMBLE__CHANNEL__REMOVE__PLAYERS__ONE_PLAYER_REMOVED_REQUEST_SUCCEED,

	// Code when several players has been removed
	MUMBLE__CHANNEL__REMOVE__PLAYERS__SEVERAL_PLAYERS_REMOVED_REQUEST_SUCCEED,

	// Code for the "mumble channel rename" command -----------------------------
	MUMBLE__CHANNEL__RENAME__EXPLANATION,

	// Code when the channel name is missing
	MUMBLE__CHANNEL__RENAME__NAME_IS_MISSING,

	// Code when the channel does not exist
	MUMBLE__CHANNEL__RENAME__CHANNEL_NOT_FOUND,

	// Code when the new channel name is missing
	MUMBLE__CHANNEL__RENAME__NEW_NAME_IS_MISSING,

	// Code when the channel is already registered
	MUMBLE__CHANNEL__RENAME__CHANNEL_ALREADY_REGISTERED,

	// Code when the channel has been renamed on the remote
	MUMBLE__CHANNEL__RENAME__REQUEST_SUCCEED,

	// Code for the "mumble channel soundModifier" command ----------------------
	MUMBLE__CHANNEL__SOUND_MODIFIER__EXPLANATION,

	// Code for the "mumble channel soundModifier details" command --------------
	MUMBLE__CHANNEL__SOUND_MODIFIER__DETAILS__EXPLANATION,

	// Code when the channel name is missing
	MUMBLE__CHANNEL__SOUND_MODIFIER__DETAILS__CHANNEL_NAME_IS_MISSING,

	// Code when the channel does not exist
	MUMBLE__CHANNEL__SOUND_MODIFIER__DETAILS__CHANNEL_NOT_FOUND,

	// Code to display the properties of a sound modifier
	MUMBLE__CHANNEL__SOUND_MODIFIER__DETAILS__SOUND_MODIFIER_DETAILS,

	// Code for the "mumble players" command -------------------------------------
	MUMBLE__PLAYERS__EXPLANATION,

	// Code for the "mumble players add" command ---------------------------------
	MUMBLE__PLAYERS__ADD__EXPLANATION,

	// Code for the administrator status completion
	MUMBLE__PLAYERS__ADD__ADMIN_COMPLETION,

	// Code for the x coordinate completion
	MUMBLE__PLAYERS__ADD__X_COMPLETION,

	// Code for the y coordinate completion
	MUMBLE__PLAYERS__ADD__Y_COMPLETION,

	// Code for the z coordinate completion
	MUMBLE__PLAYERS__ADD__Z_COMPLETION,

	// Code for the yaw coordinate completion
	MUMBLE__PLAYERS__ADD__YAW_COMPLETION,

	// Code for the pitch coordinate completion
	MUMBLE__PLAYERS__ADD__PITCH_COMPLETION,

	// Code when the player name is missing
	MUMBLE__PLAYERS__ADD__NAME_IS_MISSING,

	// Code when the player is already registered
	MUMBLE__PLAYERS__ADD__PLAYER_ALREADY_REGISTERED,

	// Code when the game address is missing
	MUMBLE__PLAYERS__ADD__ADDRESS_IS_MISSING,

	// Code when no IP address for the host could be found
	MUMBLE__PLAYERS__ADD__ADDRESS_NOT_FOUND,

	// Code when the game port is missing
	MUMBLE__PLAYERS__ADD__PORT_NUMBER_IS_MISSING,

	// Code when the port number has an invalid format
	MUMBLE__PLAYERS__ADD__PORT_NUMBER_BAD_FORMAT,

	// Code when the port number has an invalid range
	MUMBLE__PLAYERS__ADD__PORT_NUMBER_BAD_RANGE,

	// Code when the administrator status is missing
	MUMBLE__PLAYERS__ADD__ADMIN_IS_MISSING,

	// Code when the administrator status has an invalid format
	MUMBLE__PLAYERS__ADD__ADMIN_BAD_FORMAT,

	// Code when the x coordinate is missing
	MUMBLE__PLAYERS__ADD__X_IS_MISSING,

	// Code when the x coordinate has an invalid format
	MUMBLE__PLAYERS__ADD__X_BAD_FORMAT,

	// Code when the y coordinate is missing
	MUMBLE__PLAYERS__ADD__Y_IS_MISSING,

	// Code when the y coordinate has an invalid format
	MUMBLE__PLAYERS__ADD__Y_BAD_FORMAT,

	// Code when the z coordinate is missing
	MUMBLE__PLAYERS__ADD__Z_IS_MISSING,

	// Code when the z coordinate has an invalid format
	MUMBLE__PLAYERS__ADD__Z_BAD_FORMAT,

	// Code when the yaw angle is missing
	MUMBLE__PLAYERS__ADD__YAW_IS_MISSING,

	// Code when the yaw angle has an invalid format
	MUMBLE__PLAYERS__ADD__YAW_BAD_FORMAT,

	// Code when the pitch angle is missing
	MUMBLE__PLAYERS__ADD__PITCH_IS_MISSING,

	// Code when the pitch angle has an invalid format
	MUMBLE__PLAYERS__ADD__PITCH_BAD_FORMAT,

	// Code when the player has been added on the remote
	MUMBLE__PLAYERS__ADD__REQUEST_SUCCEED,

	// Code for the "mumble players remove" command ------------------------------
	MUMBLE__PLAYERS__REMOVE__EXPLANATION,

	// Code when the player name is missing
	MUMBLE__PLAYERS__REMOVE__NAME_IS_MISSING,

	// Code when the player does not exists
	MUMBLE__PLAYERS__REMOVE__PLAYER_NOT_FOUND,

	// Code when the player has been removed on the remote
	MUMBLE__PLAYERS__REMOVE__REQUEST_SUCCEED,

	// Code for the "mumble players remove" command ------------------------------
	MUMBLE__PLAYERS__RENAME__EXPLANATION,

	// Code when the player name is missing
	MUMBLE__PLAYERS__RENAME__NAME_IS_MISSING,

	// Code when the player does not exist
	MUMBLE__PLAYERS__RENAME__PLAYER_NOT_FOUND,

	// Code when the new player name is missing
	MUMBLE__PLAYERS__RENAME__NEW_NAME_IS_MISSING,

	// Code when the player is already registered
	MUMBLE__PLAYERS__RENAME__PLAYER_ALREADY_REGISTERED,

	// Code when the player has been renamed on the remote
	MUMBLE__PLAYERS__RENAME__REQUEST_SUCCEED,

	// Code for the "mumble players online" command ------------------------------
	MUMBLE__PLAYERS__ONLINE__EXPLANATION,

	// Code for the online status completion
	MUMBLE__PLAYERS__ONLINE__COMPLETION,

	// Code when the player name is missing
	MUMBLE__PLAYERS__ONLINE__NAME_IS_MISSING,

	// Code when the player does not exist
	MUMBLE__PLAYERS__ONLINE__PLAYER_NOT_FOUND,

	// Code when the player does not exist
	MUMBLE__PLAYERS__ONLINE__STATUS_IS_MISSING,

	// Code when the online status has a bad format
	MUMBLE__PLAYERS__ONLINE__ONLINE_BAD_FORMAT,

	// Code when the player online status has been updated on the remote
	MUMBLE__PLAYERS__ONLINE__ONLINE_REQUEST_SUCCEED,

	// Code when the player online status has been updated on the remote
	MUMBLE__PLAYERS__ONLINE__OFFLINE_REQUEST_SUCCEED,

	// Code for the "mumble players gameAddress" command -------------------------
	MUMBLE__PLAYERS__GAME_ADDRESS__EXPLANATION,

	// Code when the player name is missing
	MUMBLE__PLAYERS__GAME_ADDRESS__NAME_IS_MISSING,

	// Code when the player does not exist
	MUMBLE__PLAYERS__GAME_ADDRESS__PLAYER_NOT_FOUND,

	// Code when the address is missing
	MUMBLE__PLAYERS__GAME_ADDRESS__ADDRESS_IS_MISSING,

	// Code when no IP address for the host could be found
	MUMBLE__PLAYERS__GAME_ADDRESS__ADDRESS_NOT_FOUND,

	// Code when the port number is missing
	MUMBLE__PLAYERS__GAME_ADDRESS__PORT_NUMBER_IS_MISSING,

	// Code when the port number has an invalid format
	MUMBLE__PLAYERS__GAME_ADDRESS__PORT_NUMBER_BAD_FORMAT,

	// Code when the port number has an invalid range
	MUMBLE__PLAYERS__GAME_ADDRESS__PORT_NUMBER_BAD_RANGE,

	// Code when the game address of a player has been updated on the remote
	MUMBLE__PLAYERS__GAME_ADDRESS__REQUEST_SUCCEED,

	// Code for the "mumble players mute" command --------------------------------
	MUMBLE__PLAYERS__ADMIN__EXPLANATION,

	// Code for the administrator status completion
	MUMBLE__PLAYERS__ADMIN__COMPLETION,

	// Code when the player name is missing
	MUMBLE__PLAYERS__ADMIN__NAME_IS_MISSING,

	// Code when the player does not exist
	MUMBLE__PLAYERS__ADMIN__PLAYER_NOT_FOUND,

	// Code when the administrator status is missing
	MUMBLE__PLAYERS__ADMIN__STATUS_IS_MISSING,

	// Code when the administrator status has a bad format
	MUMBLE__PLAYERS__ADMIN__STATUS_BAD_FORMAT,

	// Code when the player is administrator
	MUMBLE__PLAYERS__ADMIN__ADMIN_REQUEST_SUCCEED,

	// Code when the player is not and administrator
	MUMBLE__PLAYERS__ADMIN__NOT_ADMIN_REQUEST_SUCCEED,

	// Code for the "mumble players mute" command --------------------------------
	MUMBLE__PLAYERS__MUTE__EXPLANATION,

	// Code for the mute status completion
	MUMBLE__PLAYERS__MUTE__COMPLETION,

	// Code when the player name is missing
	MUMBLE__PLAYERS__MUTE__NAME_IS_MISSING,

	// Code when the player does not exist
	MUMBLE__PLAYERS__MUTE__PLAYER_NOT_FOUND,

	// Code when the mute status is missing
	MUMBLE__PLAYERS__MUTE__STATUS_IS_MISSING,

	// Code when the mute status has a bad format
	MUMBLE__PLAYERS__MUTE__STATUS_BAD_FORMAT,

	// Code when the player is mute
	MUMBLE__PLAYERS__MUTE__MUTED_REQUEST_SUCCEED,

	// Code when the player is unmute
	MUMBLE__PLAYERS__MUTE__UNMUTED_REQUEST_SUCCEED,

	// Code for the "mumble players muteBy" command ------------------------------
	MUMBLE__PLAYERS__MUTE_BY__EXPLANATION,

	// Code for the mute by status completion
	MUMBLE__PLAYERS__MUTE_BY__COMPLETION,

	// Code when the target player is missing
	MUMBLE__PLAYERS__MUTE_BY__TARGET_NAME_IS_MISSING,

	// Code when the target player does not exist
	MUMBLE__PLAYERS__MUTE_BY__TARGET_NOT_FOUND,

	// Code when the source player is missing
	MUMBLE__PLAYERS__MUTE_BY__SOURCE_NAME_IS_MISSING,

	// Code when the source player does not exist
	MUMBLE__PLAYERS__MUTE_BY__SOURCE_NOT_FOUND,

	// Code when the mute status is missing
	MUMBLE__PLAYERS__MUTE_BY__STATUS_IS_MISSING,

	// Code when the source player does not exist
	MUMBLE__PLAYERS__MUTE_BY__STATUS_BAD_FORMAT,

	// Code when the source player does not exist
	MUMBLE__PLAYERS__MUTE_BY__MUTE_REQUEST_SUCCEED,

	// Code when the source player does not exist
	MUMBLE__PLAYERS__MUTE_BY__UNMUTE_REQUEST_SUCCEED,

	// Code for the "mumble players deafen" command ------------------------------
	MUMBLE__PLAYERS__DEAFEN__EXPLANATION,

	// Code for the deafen status completion
	MUMBLE__PLAYERS__DEAFEN__COMPLETION,

	// Code when the player name is missing
	MUMBLE__PLAYERS__DEAFEN__NAME_IS_MISSING,

	// Code when the player does not exist
	MUMBLE__PLAYERS__DEAFEN__PLAYER_NOT_FOUND,

	// Code when the deafen status is missing
	MUMBLE__PLAYERS__DEAFEN__STATUS_IS_MISSING,

	// Code when the deafen status has a bad format
	MUMBLE__PLAYERS__DEAFEN__STATUS_BAD_FORMAT,

	// Code when the player is deafen
	MUMBLE__PLAYERS__DEAFEN__DEAFEND_REQUEST_SUCCEED,

	// Code when the player is undeafen
	MUMBLE__PLAYERS__DEAFEN__UNDEAFEND_REQUEST_SUCCEED,

	// Code for the "mumble players kick" command -------------------------------
	MUMBLE__PLAYERS__KICK__EXPLANATION,

	// Code when name of the player to kick is missing
	MUMBLE__PLAYERS__KICK__KICKED_PLAYER_NAME_IS_MISSING,

	// Code when the player to kick does not exist
	MUMBLE__PLAYERS__KICK__KICKED_PLAYER_NOT_FOUND,

	// Code when name of the kicking player is missing
	MUMBLE__PLAYERS__KICK__KICKING_PLAYER_NAME_IS_MISSING,

	// Code when the kicking player does not exist
	MUMBLE__PLAYERS__KICK__KICKING_PLAYER_NOT_FOUND,

	// Code when the kicking player is not an administrator
	MUMBLE__PLAYERS__KICK__KICKING_PLAYER_NOT_ADMIN,

	// Code when the player to kick is not registered in a channel
	MUMBLE__PLAYERS__KICK__KICKED_PLAYER_NOT_REGISTERED_IN_CHANNEL,

	// Code when a player has been kicked from a channel
	MUMBLE__PLAYERS__KICK__REQUEST_SUCCEED,

	// Code for the "mumble players position" command ---------------------------
	MUMBLE__PLAYERS__POSITION__EXPLANATION,

	// Code for the X coordinate completion,
	MUMBLE__PLAYERS__POSITION__X_COMPLETION,

	// Code for the Y coordinate completion,
	MUMBLE__PLAYERS__POSITION__Y_COMPLETION,

	// Code for the Z coordinate completion,
	MUMBLE__PLAYERS__POSITION__Z_COMPLETION,

	// Code for the Yaw angle completion,
	MUMBLE__PLAYERS__POSITION__YAW_COMPLETION,

	// Code for the X coordinate completion,
	MUMBLE__PLAYERS__POSITION__PITCH_COMPLETION,

	// Code when the player name is missing,
	MUMBLE__PLAYERS__POSITION__PLAYER_NAME_IS_MISSING,

	// Code when the player name is missing,
	MUMBLE__PLAYERS__POSITION__PLAYER_NOT_FOUND,

	// Code when the x coordinate is missing
	MUMBLE__PLAYERS__POSITION__X_IS_MISSING,

	// Code when the x coordinate has a bad format
	MUMBLE__PLAYERS__POSITION__X_BAD_FORMAT,

	// Code when the y coordinate is missing
	MUMBLE__PLAYERS__POSITION__Y_IS_MISSING,

	// Code when the y coordinate has a bad format
	MUMBLE__PLAYERS__POSITION__Y_BAD_FORMAT,

	// Code when the z coordinate is missing
	MUMBLE__PLAYERS__POSITION__Z_IS_MISSING,

	// Code when the z coordinate has a bad format
	MUMBLE__PLAYERS__POSITION__Z_BAD_FORMAT,

	// Code when the yaw coordinate is missing
	MUMBLE__PLAYERS__POSITION__YAW_IS_MISSING,

	// Code when the yaw coordinate has a bad format
	MUMBLE__PLAYERS__POSITION__YAW_BAD_FORMAT,

	// Code when the pitch coordinate is missing
	MUMBLE__PLAYERS__POSITION__PITCH_IS_MISSING,

	// Code when the pitch coordinate has a bad format
	MUMBLE__PLAYERS__POSITION__PITCH_BAD_FORMAT,

	// Code when the player's position has been updated on the server
	MUMBLE__PLAYERS__POSITION__REQUEST_SUCCEED,

	// Code for the "mumble details" command -------------------------------------
	MUMBLE__DETAILS__EXPLANATION,

	// Code for the server name
	MUMBLE__DETAILS__SERVER_NAME,

	// Code for the server IP address
	MUMBLE__DETAILS__SERVER_IP_ADDRESS,

	// Code for the server reachable status
	MUMBLE__DETAILS__SERVER_REACHABLE_STATUS,

	// Code when the server is not reachable
	MUMBLE__DETAILS__SERVER_NOT_REACHABLE,

	// Code When the server is reachable
	MUMBLE__DETAILS__SERVER_REACHABLE,

	// Code for the details of sound modifiers
	MUMBLE__DETAILS__SOUND_MODIFIERS,

	// Code for the sound modifier's name
	MUMBLE__DETAILS__SOUND_MODIFIER_NAME,

	// Code for the details of a sound modifier's parameters
	MUMBLE__DETAILS__SOUND_MODIFIER_PARAMETERS,

	// Code for the parameter's name
	MUMBLE__DETAILS__PARAMETER_NAME,

	// Code for the parameter's value
	MUMBLE__DETAILS__PARAMETER_VALUE,

	// Code for the parameter's default value
	MUMBLE__DETAILS__PARAMETER_DEFAULT_VALUE,

	// Code for the parameter's minimum value
	MUMBLE__DETAILS__PARAMETER_MINIMUM_VALUE,

	// Code for the parameter's maximum value
	MUMBLE__DETAILS__PARAMETER_MAXIMUM_VALUE,

	// Code for the details of players
	MUMBLE__DETAILS__PLAYERS,

	// Code for the player's name
	MUMBLE__DETAILS__PLAYER_NAME,

	// Code for the player's identifier
	MUMBLE__DETAILS__PLAYER_IDENTIFIER,

	// Code for the player's online status
	MUMBLE__DETAILS__PLAYER_ONLINE_STATUS,

	// Code when the player is not connected in game
	MUMBLE__DETAILS__PLAYER_OFFLINE,

	// Code when the player is connected in game
	MUMBLE__DETAILS__PLAYER_ONLINE,

	// Code for the player's game address
	MUMBLE__DETAILS__PLAYER_GAME_ADDRESS,

	// Code for the player's administrator status
	MUMBLE__DETAILS__PLAYER_ADMIN_STATUS,

	// Code when the player is an administrator
	MUMBLE__DETAILS__PLAYER_ADMIN,

	// Code when the player is not an administrator
	MUMBLE__DETAILS__PLAYER_NOT_ADMIN,

	// Code for the player's mute status
	MUMBLE__DETAILS__PLAYER_MUTE_STATUS,

	// Code when the player is mute
	MUMBLE__DETAILS__PLAYER_MUTE,

	// Code when the player is not mute
	MUMBLE__DETAILS__PLAYER_NOT_MUTE,

	// Code for the player's mute by status
	MUMBLE__DETAILS__PLAYER_MUTE_BY,

	// Code for the player's deafen status
	MUMBLE__DETAILS__PLAYER_DEAFEN_STATUS,

	// Code when the player is deafen
	MUMBLE__DETAILS__PLAYER_DEAFEN,

	// Code when the player is not deafen
	MUMBLE__DETAILS__PLAYER_NOT_DEAFEN,

	// Code for the player's position
	MUMBLE__DETAILS__PLAYER_POSITION,

	// Code for the details of channels
	MUMBLE__DETAILS__CHANNELS,

	// Code for the channel's name
	MUMBLE__DETAILS__CHANNEL_NAME,

	// Code for the details of the channel's sound modifier
	MUMBLE__DETAILS__CHANNEL_SOUND_MODIFIER,

	// Code for the complete server configuration
	MUMBLE__DETAILS__SERVER,

	;

	@Override
	public String getCode() {
		return name();
	}

}