package fr.pederobien.mumble.commandline.impl;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import fr.pederobien.mumble.client.external.interfaces.IChannel;
import fr.pederobien.mumble.client.external.interfaces.IParameter;
import fr.pederobien.mumble.client.external.interfaces.IResponse;
import fr.pederobien.mumble.client.external.interfaces.ISoundModifier;

public class ParameterValueNode extends ParameterNode {

	/**
	 * Creates a node in order to update the current value of a parameter.
	 * 
	 * @param parameter The parameter associated to this node.
	 */
	protected ParameterValueNode(Supplier<IParameter<?>> parameter) {
		super(parameter, "value", EMumbleClientCode.PARAMETER__VALUE__EXPLANATION, p -> p != null);
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		switch (args.length) {
		case 1:
			return asList(getMessage(EMumbleClientCode.PARAMETER__VALUE__COMPLETION));
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(String[] args) {
		Object value;
		try {
			value = getParameter().getType().getValue(args[0]);
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.PARAMETER__VALUE__VALUE_IS_MISSING, getParameter().getName());
			return false;
		} catch (Exception e) {
			send(EMumbleClientCode.PARAMETER__VALUE__VALUE_BAD_FORMAT, getParameter().getName());
			return false;
		}

		ISoundModifier soundModifier = getParameter().getSoundModifier();
		IChannel channel = soundModifier.getChannel();
		Consumer<IResponse> update = response -> {
			if (response.hasFailed())
				send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
			else
				send(EMumbleClientCode.PARAMETER__VALUE__REQUEST_SUCCEED, getParameter().getName(), soundModifier.getName(), channel.getName(), value);
		};

		try {
			getParameter().setValue(value, update);
		} catch (IllegalArgumentException e) {
			// When the parameter as a range
			send(EMumbleClientCode.PARAMETER__VALUE__VALUE_OUT_OF_RANGE, getRangeParameter().getMin(), getRangeParameter().getMax());
		}
		return true;
	}
}
