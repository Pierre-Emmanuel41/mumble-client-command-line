package fr.pederobien.mumble.commandline.client;

import fr.pederobien.mumble.commandline.client.impl.BeginContext;

public class MumbleClientCommandLine {

	public static void main(String[] args) {
		new BeginContext().initialize().start();
	}
}
