package fr.pederobien.mumble.commandline.impl;

import java.nio.file.Paths;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import fr.pederobien.commandtree.events.NodeEvent;
import fr.pederobien.commandtree.exceptions.NodeNotFoundException;
import fr.pederobien.commandtree.exceptions.NotAvailableArgumentException;
import fr.pederobien.communication.event.ConnectionEvent;
import fr.pederobien.dictionary.event.DictionaryEvent;
import fr.pederobien.dictionary.impl.JarXmlDictionaryParser;
import fr.pederobien.dictionary.impl.MessageEvent;
import fr.pederobien.dictionary.impl.XmlDictionaryParser;
import fr.pederobien.dictionary.interfaces.IDictionaryParser;
import fr.pederobien.mumble.commandline.interfaces.ICode;
import fr.pederobien.utils.AsyncConsole;
import fr.pederobien.utils.event.EventLogger;

public class BeginContext {
	private static final String FILE_PREFIX = "file";
	private static final String JAR_PREFIX = "jar";
	private static final String DEV_DICTIONARY_FOLDER = "src/main/resources/dictionaries/";
	private static final String PROD_DICTIONARY_FOLDER = "resources/dictionaries/";

	private MumbleClientCommandTree tree;
	private Scanner scanner;
	private AtomicBoolean isInitialized;

	public BeginContext() {
		isInitialized = new AtomicBoolean(false);
	}

	/**
	 * Initialize the context.
	 * 
	 * @return This initialized context.
	 */
	public BeginContext initialize() {
		if (!isInitialized.compareAndSet(false, true))
			return this;

		EventLogger.instance().newLine(true).timeStamp(true).ignore(DictionaryEvent.class).ignore(ConnectionEvent.class).ignore(NodeEvent.class).register();

		tree = new MumbleClientCommandTree();
		scanner = new Scanner(System.in);

		registerDictionaries();

		return this;
	}

	/**
	 * Start waiting for user input in order to run commands
	 */
	public void start() {
		if (!isInitialized.get())
			throw new IllegalStateException("The context has not been initialized");

		send(EMumbleClientCode.MUMBLE__STARTING);

		while (true) {
			AsyncConsole.print(">");
			String command = scanner.nextLine();

			if (command.trim().equals("stop")) {
				// Disconnecting before stopping program
				try {
					tree.getRoot().onCommand(new String[] { tree.getDisconnectNode().getLabel() });
				} catch (NotAvailableArgumentException e) {
					// do nothing
				}
				break;
			}

			try {
				tree.getRoot().onCommand(command.split(" "));
				Thread.sleep(200);
			} catch (NodeNotFoundException e) {
				send(EMumbleClientCode.MUMBLE__NODE_NOT_FOUND, e.getNotFoundArgument());
			} catch (NotAvailableArgumentException e) {
				send(EMumbleClientCode.MUMBLE__NODE_NOT_AVAILABLE, e.getArgument());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		send(EMumbleClientCode.MUMBLE__STOPPING);
		scanner.close();
	}

	/**
	 * @return The command tree associated to this context.
	 */
	public MumbleClientCommandTree getTree() {
		return tree;
	}

	/**
	 * @return The scanner associated to {@link System#in} inputStream.
	 */
	public Scanner getScanner() {
		return scanner;
	}

	private void registerDictionaries() {
		String url = getClass().getResource(getClass().getSimpleName() + ".class").toExternalForm();
		IDictionaryParser parser = null;
		String dictionaryFolder = null;

		// Case Development environment
		if (url.startsWith(FILE_PREFIX)) {
			parser = new XmlDictionaryParser();
			dictionaryFolder = DEV_DICTIONARY_FOLDER;

			// Case production environment
		} else if (url.startsWith(JAR_PREFIX)) {
			parser = new JarXmlDictionaryParser(Paths.get(url.split("!")[0].substring(String.format("%s:%s:/", FILE_PREFIX, JAR_PREFIX).length()).replace("%20", " ")));
			dictionaryFolder = PROD_DICTIONARY_FOLDER;
		}

		if (parser == null)
			throw new IllegalStateException("Technical error, the environment is neither a development environment nor a production environment");

		String[] dictionaries = new String[] { "English.xml", "French.xml" };
		for (String dictionary : dictionaries)
			try {
				MumbleClientDictionaryContext.instance().register(parser.parse(dictionaryFolder.concat(dictionary)));
			} catch (Exception e) {
				AsyncConsole.println(e);
				for (StackTraceElement element : e.getStackTrace())
					AsyncConsole.println(element);
			}
	}

	/**
	 * Send a language sensitive message in the console.
	 * 
	 * @param code Used as key to get the right message in the right dictionary.
	 * @param args Some arguments (optional) used for dynamic messages.
	 */
	private void send(ICode code, Object... args) {
		MumbleClientDictionaryContext.instance().send(new MessageEvent(Locale.getDefault(), code.getCode(), args));
	}
}
