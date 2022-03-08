package fr.pederobien.mumble.commandline;

import java.util.Scanner;

import fr.pederobien.commandtree.events.NodeEvent;
import fr.pederobien.communication.event.ConnectionEvent;
import fr.pederobien.dictionary.event.DictionaryEvent;
import fr.pederobien.mumble.commandline.impl.BeginContext;
import fr.pederobien.utils.event.EventLogger;

public class CommandLine {

	public static void main(String[] args) {
		EventLogger.instance().newLine(true).timeStamp(true).ignore(DictionaryEvent.class).ignore(ConnectionEvent.class).ignore(NodeEvent.class).register();

		BeginContext context = new BeginContext();
		context.initialize();

		System.out.println("Starting Mumble client command line");

		Scanner scanner = new Scanner(System.in);

		String command = "";
		while (true) {
			command = scanner.nextLine();

			try {
				context.getTree().getRoot().onCommand(command.split(" "));
			} catch (Exception e) {
				// Do nothing
			}

			if (command.trim().equals("stop"))
				break;
		}

		System.out.println("Stopping Mumble client command line");

		scanner.close();
	}
}
