package fr.pederobien.mumble.commandline.impl;

import java.util.Locale;
import java.util.function.Consumer;

import fr.pederobien.commandtree.impl.CommandRootNode;
import fr.pederobien.commandtree.interfaces.ICommandRootNode;
import fr.pederobien.commandtree.interfaces.INode;
import fr.pederobien.dictionary.impl.MessageEvent;
import fr.pederobien.mumble.client.interfaces.IParameter;
import fr.pederobien.mumble.commandline.interfaces.ICode;
import fr.pederobien.utils.AsyncConsole;

public class ParameterCommandTree {
	private IParameter<?> parameter;
	private ICommandRootNode<ICode> root;
	private ParameterValueNode valueNode;
	private ParameterMinValueNode minValueNode;

	public ParameterCommandTree() {
		Consumer<INode<ICode>> displayer = node -> {
			String label = node.getLabel();
			String explanation = MumbleClientDictionaryContext.instance().getMessage(new MessageEvent(Locale.getDefault(), node.getExplanation().toString()));
			AsyncConsole.println(String.format("%s - %s", label, explanation));
		};

		root = new CommandRootNode<ICode>("parameter", EMumbleClientCode.PARAMETER__ROOT__EXPLANATION, () -> true, displayer);
		root.add(valueNode = new ParameterValueNode(() -> getParameter()));
		root.add(minValueNode = new ParameterMinValueNode(() -> getParameter()));
	}

	/**
	 * @return The underlying parameter managed by this command tree.
	 */
	public IParameter<?> getParameter() {
		return parameter;
	}

	/**
	 * Set the parameter managed by this command tree.
	 * 
	 * @param parameter The new parameter managed by this command tree.
	 */
	public void setParameter(IParameter<?> parameter) {
		this.parameter = parameter;
	}

	/**
	 * @return The root of this command tree.
	 */
	public ICommandRootNode<ICode> getRoot() {
		return root;
	}

	/**
	 * @return The node that modifies the value of the current parameter.
	 */
	public ParameterValueNode getValueNode() {
		return valueNode;
	}

	public ParameterMinValueNode getMinValueNode() {
		return minValueNode;
	}
}
