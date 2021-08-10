package fsm.pattern;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        FiniteStateMachine fsm = DriverChangeState.createStateMachine();
        IContext fsmContext = fsm.getContext();
        fsmContext.bind("name", "harshil");
        Map<String, Object> payload = new HashMap<>();
        payload.put("source", "SWEEPER");
        payload.put("event", "SAVED_STATE_EVENT");
        CommonOrderEvent event = DriverChangeEventFactory.createNewEvent(payload);
        fsm.onEvent(event);
    }
}
