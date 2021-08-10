package fsm.pattern.custom;

import fsm.pattern.Action;
import fsm.pattern.IContext;

public class ThirdAction extends Action<Object> {
    @Override
    public Object execute(IContext context) {
        System.out.println("inside third action");
        return context;
    }

    @Override
    public Boolean isApplicable(IContext context) {
        return true;
    }
}
