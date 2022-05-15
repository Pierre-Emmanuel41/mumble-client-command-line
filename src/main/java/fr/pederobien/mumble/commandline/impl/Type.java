package fr.pederobien.mumble.commandline.impl;

public enum Type {
	EXTERNAL_TO_SERVER("external"),

	PLAYER_TO_SERVER("player");

	private static final String EXTERNAL = "external";
	private static final String PLAYER = "player";
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
		switch (name) {
		case EXTERNAL:
			return EXTERNAL_TO_SERVER;
		case PLAYER:
			return PLAYER_TO_SERVER;
		default:
			return null;
		}
	}
}
