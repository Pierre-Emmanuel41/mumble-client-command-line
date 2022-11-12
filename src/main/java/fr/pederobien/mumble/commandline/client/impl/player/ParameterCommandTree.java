package fr.pederobien.mumble.commandline.client.impl.player;

import java.util.Locale;
import java.util.function.Consumer;

import fr.pederobien.commandline.impl.CommandLineDictionaryContext;
import fr.pederobien.commandtree.impl.CommandRootNode;
import fr.pederobien.commandtree.interfaces.ICommandRootNode;
import fr.pederobien.commandtree.interfaces.INode;
import fr.pederobien.dictionary.impl.MessageEvent;
import fr.pederobien.dictionary.interfaces.ICode;
import fr.pederobien.mumble.client.player.interfaces.IParameter;
import fr.pederobien.mumble.commandline.client.impl.EMumbleClientCode;
import fr.pederobien.utils.AsyncConsole;

public class ParameterCommandTree {
	private IParameter<?> parameter;
	private ICommandRootNode<ICode> root;
	private ParameterValueNode valueNode;
	private ParameterMinValueNode minValueNode;
	private ParameterMaxValueNode maxValueNode;

	public ParameterCommandTree() {
		Consumer<INode<ICode>> displayer = node -> {
			String label = node.getLabel();
			String explanation = CommandLineDictionaryContext.instance().getMessage(new MessageEvent(Locale.getDefault(), node.getExplanation()));
			AsyncConsole.println(String.format("%s - %s", label, explanation));
		};

		root = new CommandRootNode<ICode>("parameter", EMumbleClientCode.PARAMETER__ROOT__EXPLANATION, () -> true, displayer);
		root.add(valueNode = new ParameterValueNode(() -> getParameter()));
		root.add(minValueNode = new ParameterMinValueNode(() -> getParameter()));
		root.add(maxValueNode = new ParameterMaxValueNode(() -> getParameter()));
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

	/**
	 * @return The node that modifies the minimum value of the current parameter.
	 */
	public ParameterMinValueNode getMinValueNode() {
		return minValueNode;
	}

	/**
	 * @return The node that modifies the maximum value of the current parameter.
	 */
	public ParameterMaxValueNode getMaxValueNode() {
		return maxValueNode;
	}
}
