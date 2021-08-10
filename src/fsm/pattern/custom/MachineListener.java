package fsm.pattern.custom;

import fsm.pattern.IContext;
import fsm.pattern.IEvent;
import fsm.pattern.IStateMachine;
import fsm.pattern.IStateMachineListener;

public class MachineListener implements IStateMachineListener {

    @Override
    public void onEntry(IEvent event, IContext context) {
        System.out.println("in machine listener");
        context.bind(IStateMachine.CURRENT_EVENT_KEY, event);
    }
}
