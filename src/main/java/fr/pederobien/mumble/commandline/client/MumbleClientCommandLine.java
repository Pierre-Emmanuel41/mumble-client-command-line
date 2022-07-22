package fr.pederobien.mumble.commandline.client;

import fr.pederobien.commandline.CommandLine;
import fr.pederobien.commandline.CommandLine.CommandLineBuilder;
import fr.pederobien.commandtree.events.NodeEvent;
import fr.pederobien.communication.event.ConnectionEvent;
import fr.pederobien.dictionary.event.DictionaryEvent;
import fr.pederobien.mumble.client.external.event.PlayerPositionChangePostEvent;
import fr.pederobien.mumble.client.external.event.PlayerPositionChangePreEvent;
import fr.pederobien.mumble.commandline.client.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.client.impl.MumbleClientCommandTree;
import fr.pederobien.sound.event.ProjectSoundEvent;
import fr.pederobien.utils.event.EventLogger;
import fr.pederobien.vocal.client.event.VocalPlayerSpeakPostEvent;
import fr.pederobien.vocal.client.event.VocalPlayerSpeakPreEvent;

public class MumbleClientCommandLine {
	private static final String DEV_DICTIONARY_FOLDER = "src/main/resources/dictionaries/";
	private static final String PROD_DICTIONARY_FOLDER = "resources/dictionaries/mumble/client/";

	private static MumbleClientCommandTree tree;
	private static CommandLine commandLine;

	public static void main(String[] args) {
		tree = new MumbleClientCommandTree();

		CommandLineBuilder builder = new CommandLineBuilder(root -> {
			EventLogger.instance().newLine(true).timeStamp(true).register();

			EventLogger.instance().ignore(DictionaryEvent.class);
			EventLogger.instance().ignore(ConnectionEvent.class);
			EventLogger.instance().ignore(NodeEvent.class);
			EventLogger.instance().ignore(ProjectSoundEvent.class);
			EventLogger.instance().ignore(VocalPlayerSpeakPreEvent.class);
			EventLogger.instance().ignore(VocalPlayerSpeakPostEvent.class);
			EventLogger.instance().ignore(PlayerPositionChangePreEvent.class);
			EventLogger.instance().ignore(PlayerPositionChangePostEvent.class);

			String dictionaryFolder = commandLine.getEnvironment() == CommandLine.DEVELOPMENT_ENVIRONMENT ? DEV_DICTIONARY_FOLDER : PROD_DICTIONARY_FOLDER;
			commandLine.registerDictionaries(dictionaryFolder, new String[] { "English.xml", "French.xml" });
			return true;
		});

		builder.onStart((root, arguments) -> {
			commandLine.send(EMumbleClientCode.MUMBLE__STARTING);
			if (arguments.length == 0)
				return true;

			if (arguments.length < 3) {
				commandLine.send(EMumbleClientCode.MUMBLE__STARTING__IGNORING_ARGUMENTS__NOT_ENOUGH_ARGUMENT);
				return true;
			}

			String[] commands = new String[arguments.length + 1];
			commands[0] = "connect";
			for (int i = 0; i < arguments.length; i++)
				commands[i + 1] = arguments[i];

			root.onCommand(commands);
			return true;
		});

		builder.onStop(root -> commandLine.send(EMumbleClientCode.MUMBLE__STOPPING));

		commandLine = builder.build(tree.getRoot(), args);
		commandLine.start();
	}
}
