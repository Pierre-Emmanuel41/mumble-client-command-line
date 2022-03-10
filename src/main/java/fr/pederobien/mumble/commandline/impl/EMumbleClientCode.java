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

	// Code when the IP address is not IPv4
	MUMBLE__CONNECT__ADDRESS_NOT_IPv4,

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

	// Code for the "mumble disconnect" command ----------------------------------------
	MUMBLE__DISCONNECT__EXPLANATION,

	// Code when the connection is aborted
	MUMBLE__DISCONNECT__CONNECTION_ABORTED,

	// Code for the "mumble channels" command ------------------------------------
	MUMBLE__CHANNELS__EXPLANATION,

	// Code for the "mumble channels add" command --------------------------------
	MUMBLE__CHANNELS__ADD__EXPLANATION,

	// Code when the channel name is missing
	MUMBLE__CHANNELS__ADD__NAME_IS_MISSING,

	// Code when the channel is already registered
	MUMBLE__CHANNELS__ADD__CHANNEL_ALREADY_REGISTERED,

	// Code when the sound modifier name is missing
	MUMBLE__CHANNELS__ADD__SOUND_MODIFIER_IS_MISSING,

	// Code when the sound modifier does not exist
	MUMBLE__CHANNELS__ADD__SOUND_MODIFIER_NOT_FOUND,

	// Code when the channel has been added on the remote
	MUMBLE__CHANNELS__ADD__REQUEST_SUCCEED,

	// Code for the "mumble channels remove" command -----------------------------
	MUMBLE__CHANNELS__REMOVE__EXPLANATION,

	// Code when the channel name is missing
	MUMBLE__CHANNELS__REMOVE__NAME_IS_MISSING,

	// Code when the channel name is missing
	MUMBLE__CHANNELS__REMOVE__CHANNEL_NOT_FOUND,

	// Code when the channel has been removed from the remote
	MUMBLE__CHANNELS__REMOVE__REQUEST_SUCCEED,

	// Code for the "mumble channels rename" command -----------------------------
	MUMBLE__CHANNELS__RENAME__EXPLANATION,

	// Code when the channel name is missing
	MUMBLE__CHANNELS__RENAME__NAME_IS_MISSING,

	// Code when the channel does not exist
	MUMBLE__CHANNELS__RENAME__CHANNEL_NOT_FOUND,

	// Code when the new channel name is missing
	MUMBLE__CHANNELS__RENAME__NEW_NAME_IS_MISSING,

	// Code when the channel is already registered
	MUMBLE__CHANNELS__RENAME__CHANNEL_ALREADY_REGISTERED,

	// Code when the channel has been renamed on the remote
	MUMBLE__CHANNELS__RENAME__REQUEST_SUCCEED,

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

	// Code when the IP address is not IPv4
	MUMBLE__PLAYERS__ADD__ADDRESS_NOT_IPv4,

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
	MUMBLE__PLAYERS__ONLINE__ONLINE_BAD_FORMAT,

	// Code when the player online status has been updated on the remote
	MUMBLE__PLAYERS__ONLINE__ONLINE_REQUEST_SUCCEED,

	// Code when the player online status has been updated on the remote
	MUMBLE__PLAYERS__ONLINE__OFFLINE_REQUEST_SUCCEED,

	;

	@Override
	public String getCode() {
		return name();
	}

}