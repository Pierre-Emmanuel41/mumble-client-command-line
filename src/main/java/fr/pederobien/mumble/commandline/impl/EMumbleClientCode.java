package fr.pederobien.mumble.commandline.impl;

import fr.pederobien.mumble.commandline.interfaces.ICode;

public enum EMumbleClientCode implements ICode {

	// Starting application ------------------------------------------------------
	MUMBLE__STARTING,

	// Stopping application ------------------------------------------------------
	MUMBLE__STOPPING,

	// Common codes --------------------------------------------------------------
	MUMBLE__NAME__COMPLETION,

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

	// Code for the IP address completion
	MUMBLE__CONNECT__ADDRESS_COMPLETION,

	// Code for the port number completion
	MUMBLE__CONNECT__PORT_COMPLETION,

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

	;

	@Override
	public String getCode() {
		return name();
	}

}