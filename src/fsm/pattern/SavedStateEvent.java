package fsm.pattern;

import java.util.Map;

public class SavedStateEvent extends CommonOrderEvent {

	public SavedStateEvent(Map<String, Object> payload) {
		super((String) payload.get("source"), "", payload);
	}

	@Override
	public IEventType getEventType() {
		return DriverChangeEventType.SAVED_STATE_EVENT;
	}

}
