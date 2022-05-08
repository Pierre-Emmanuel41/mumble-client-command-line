package fr.pederobien.mumble.commandline.impl;

public class DisconnectNode extends MumbleClientNode {
	private MumbleClientCommandTree tree;

	/**
	 * Creates a node that abort the connection with the remote.
	 * 
	 * @param server The server associated to this node.
	 */
	protected DisconnectNode(MumbleClientCommandTree tree) {
		super(() -> tree.getServer(), "disconnect", EMumbleClientCode.MUMBLE__DISCONNECT__EXPLANATION, s -> s != null);
		this.tree = tree;
	}

	@Override
	public boolean onCommand(String[] args) {
		if (!getServer().isReachable())
			return true;

		getServer().close();
		send(EMumbleClientCode.MUMBLE__DISCONNECT__CONNECTION_ABORTED, getServer().getAddress().getAddress().getHostAddress(), getServer().getAddress().getPort());
		tree.setServer(null);
		return true;
	}
}
