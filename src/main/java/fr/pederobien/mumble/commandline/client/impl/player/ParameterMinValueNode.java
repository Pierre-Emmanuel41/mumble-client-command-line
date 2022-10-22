package fr.pederobien.mumble.commandline.client.impl.player;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import fr.pederobien.messenger.interfaces.IResponse;
import fr.pederobien.mumble.client.player.interfaces.IChannel;
import fr.pederobien.mumble.client.player.interfaces.IParameter;
import fr.pederobien.mumble.client.player.interfaces.IRangeParameter;
import fr.pederobien.mumble.client.player.interfaces.ISoundModifier;
import fr.pederobien.mumble.commandline.client.impl.EMumbleClientCode;

public class ParameterMinValueNode extends ParameterNode {

	/**
	 * Creates a node in order to update the current value of a parameter.
	 * 
	 * @param parameter The parameter associated to this node.
	 */
	protected ParameterMinValueNode(Supplier<IParameter<?>> parameter) {
		super(parameter, "minValue", EMumbleClientCode.PARAMETER__MIN_VALUE__EXPLANATION, p -> p instanceof IRangeParameter<?>);
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		switch (args.length) {
		case 1:
			return asList(getMessage(EMumbleClientCode.PARAMETER__MIN_VALUE__COMPLETION));
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(String[] args) {
		Object min;
		try {
			min = getParameter().getType().getValue(args[0]);
		} catch (IndexOutOfBoundsException e) {
			send(EMumbleClientCode.PARAMETER__MIN_VALUE__VALUE_IS_MISSING, getParameter().getName());
			return false;
		} catch (Exception e) {
			send(EMumbleClientCode.PARAMETER__MIN_VALUE__VALUE_BAD_FORMAT, getParameter().getName());
			return false;
		}

		ISoundModifier soundModifier = getParameter().getSoundModifier();
		IChannel channel = soundModifier.getChannel();
		Consumer<IResponse> update = response -> {
			if (response.hasFailed())
				send(EMumbleClientCode.MUMBLE__REQUEST_FAILED, response.getErrorCode().getMessage());
			else
				send(EMumbleClientCode.PARAMETER__MIN_VALUE__REQUEST_SUCCEED, getParameter().getName(), soundModifier.getName(), channel.getName(), min);
		};

		try {
			getRangeParameter().setMin(min, update);
		} catch (IllegalArgumentException e) {
			send(EMumbleClientCode.PARAMETER__MIN_VALUE__VALUE_OUT_OF_RANGE, getParameter().getName(), getRangeParameter().getMax());
			return false;
		}
		return true;
	}
}
