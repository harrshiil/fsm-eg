package fsm.pattern.custom;

import fsm.pattern.Action;
import fsm.pattern.IContext;

public abstract class SecondAction extends Action<Object> {
    @Override
    public Object execute(IContext context) {
        System.out.println("inside second action");
        return context;
    }

    @Override
    public Boolean isApplicable(IContext context) {
        return false;
    }
}
