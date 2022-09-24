package fr.pederobien.mumble.commandline.client.impl;

public enum Type {
	// In order to create a stand-alone mumble server.
	STANDALONE_SERVER("Standalone"),

	// In order to create a simple mumble server.
	SIMPLE_SERVER("Simple"),

	UNKOWN("unknown");

	private String name;

	private Type(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * Get the connection type associated to the given name.
	 * 
	 * @param name The name of the connection type to retrieve.
	 * 
	 * @return The connection type if it exists, null otherwise.
	 */
	public static Type getByName(String name) {
		for (Type type : values())
			if (type.toString().equalsIgnoreCase(name))
				return type;

		return UNKOWN;
	}
}
