package fr.pederobien.mumble.commandline;

import fr.pederobien.mumble.commandline.impl.BeginContext;

public class CommandLine {

	public static void main(String[] args) {
		new BeginContext().initialize().start();
	}
}
