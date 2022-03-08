package fr.pederobien.mumble.commandline;

import java.util.Locale;

import fr.pederobien.commandtree.exceptions.NotAvailableArgumentException;
import fr.pederobien.dictionary.impl.MessageEvent;
import fr.pederobien.mumble.commandline.impl.BeginContext;
import fr.pederobien.mumble.commandline.impl.EMumbleClientCode;
import fr.pederobien.mumble.commandline.impl.MumbleClientDictionaryContext;
import fr.pederobien.utils.AsyncConsole;

public class CommandLine {

	public static void main(String[] args) {
		BeginContext context = new BeginContext();
		context.initialize();

		AsyncConsole.println(MumbleClientDictionaryContext.instance().getMessage(new MessageEvent(Locale.getDefault(), EMumbleClientCode.MUMBLE__STARTING.toString())));

		while (true) {
			AsyncConsole.print(">");
			String command = context.getScanner().nextLine();

			if (command.trim().equals("stop")) {
				// Disconnecting before stopping program
				try {
					context.getTree().getRoot().onCommand(new String[] { context.getTree().getDisconnectNode().getLabel() });
				} catch (NotAvailableArgumentException e) {
					// do nothing
				}
				break;
			}

			try {
				context.getTree().getRoot().onCommand(command.split(" "));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		AsyncConsole.println(MumbleClientDictionaryContext.instance().getMessage(new MessageEvent(Locale.getDefault(), EMumbleClientCode.MUMBLE__STOPPING.toString())));

		context.getScanner().close();
	}
}
