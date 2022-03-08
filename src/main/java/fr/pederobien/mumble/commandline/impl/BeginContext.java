package fr.pederobien.mumble.commandline.impl;

import java.nio.file.Paths;

import fr.pederobien.dictionary.impl.JarXmlDictionaryParser;
import fr.pederobien.dictionary.impl.XmlDictionaryParser;
import fr.pederobien.dictionary.interfaces.IDictionaryParser;
import fr.pederobien.utils.AsyncConsole;

public class BeginContext {
	private static final String FILE_PREFIX = "file";
	private static final String JAR_PREFIX = "jar";
	private static final String DEV_DICTIONARY_FOLDER = "src/main/resources/dictionaries/";
	private static final String PROD_DICTIONARY_FOLDER = "resources/dictionaries/";

	private MumbleClientCommandTree tree;

	public void initialize() {
		tree = new MumbleClientCommandTree();

		registerDictionaries();
	}

	/**
	 * @return The command tree associated to this context.
	 */
	public MumbleClientCommandTree getTree() {
		return tree;
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
}
