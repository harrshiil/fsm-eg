package fsm.pattern.custom;

import fsm.pattern.IContext;
import fsm.pattern.IEvent;
import fsm.pattern.IEventListener;

public class SecondEventListener implements IEventListener {
    @Override
    public void afterEvent(IEvent event, IContext context) {
        System.out.println("inside afterEvent of SecondEventListener");
    }
}
