package fsm.pattern.custom;

import fsm.pattern.IContext;
import fsm.pattern.IEvent;
import fsm.pattern.IEventListener;

public class FirstEventListener implements IEventListener {
    @Override
    public void onEvent(IEvent event, IContext context) {
        System.out.println("inside onEvent of FirstEventListener");
    }
}
