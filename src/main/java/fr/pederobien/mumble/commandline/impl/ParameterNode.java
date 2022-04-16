package fr.pederobien.mumble.commandline.impl;

import java.util.function.Function;
import java.util.function.Supplier;

import fr.pederobien.mumble.client.interfaces.IParameter;
import fr.pederobien.mumble.client.interfaces.IRangeParameter;
import fr.pederobien.mumble.commandline.interfaces.ICode;

public class ParameterNode extends MumbleClientNode {
	private Supplier<IParameter<?>> parameter;

	/**
	 * Creates a node specified by the given parameters.
	 * 
	 * @param parameter   The parameter associated to this node.
	 * @param label       The primary node name.
	 * @param explanation The explanation associated to this node.
	 * @param isAvailable True if this node is available, false otherwise.
	 */
	protected ParameterNode(Supplier<IParameter<?>> parameter, String label, ICode explanation, Function<IParameter<?>, Boolean> isAvailable) {
		super(null, label, explanation, s -> isAvailable.apply(parameter == null ? null : parameter.get()));
		this.parameter = parameter;
	}

	/**
	 * @return The parameter associated to this node.
	 */
	public IParameter<?> getParameter() {
		return parameter.get();
	}

	/**
	 * @return The parameter associated to this node.
	 */
	public IRangeParameter<?> getRangeParameter() {
		return parameter.get() instanceof IRangeParameter<?> ? (IRangeParameter<?>) parameter.get() : null;
	}
}
