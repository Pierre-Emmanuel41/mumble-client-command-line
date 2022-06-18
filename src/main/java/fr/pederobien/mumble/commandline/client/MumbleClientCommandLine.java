package fr.pederobien.mumble.commandline.client;

import fr.pederobien.commandline.CommandLine;
import fr.pederobien.commandline.CommandLine.CommandLineBuilder;
import fr.pederobien.commandtree.events.NodeEvent;
import fr.pederobien.communication.event.ConnectionEvent;
import fr.pederobien.dictionary.event.DictionaryEvent;
import fr.pederobien.mumble.commandline.client.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.client.impl.MumbleClientCommandTree;
import fr.pederobien.sound.event.SoundEvent;
import fr.pederobien.utils.event.EventLogger;

public class MumbleClientCommandLine {
	private static final String DEV_DICTIONARY_FOLDER = "src/main/resources/dictionaries/";
	private static final String PROD_DICTIONARY_FOLDER = "resources/dictionaries/mumble/client/";

	private static MumbleClientCommandTree tree;
	private static CommandLine commandLine;

	public static void main(String[] args) {
		tree = new MumbleClientCommandTree();

		CommandLineBuilder builder = new CommandLineBuilder(root -> {
			EventLogger.instance().newLine(true).timeStamp(true).ignore(DictionaryEvent.class).ignore(ConnectionEvent.class);
			EventLogger.instance().ignore(NodeEvent.class).ignore(SoundEvent.class).register();

			String dictionaryFolder = commandLine.getEnvironment() == CommandLine.DEVELOPMENT_ENVIRONMENT ? DEV_DICTIONARY_FOLDER : PROD_DICTIONARY_FOLDER;
			commandLine.registerDictionaries(dictionaryFolder, new String[] { "English.xml", "French.xml" });
			return true;
		});

		builder.onStart(root -> {
			commandLine.send(EMumbleClientCode.MUMBLE__STARTING);
			return true;
		});

		builder.onStop(root -> commandLine.send(EMumbleClientCode.MUMBLE__STOPPING));

		commandLine = builder.build(tree.getRoot());
		commandLine.start();
	}
}
