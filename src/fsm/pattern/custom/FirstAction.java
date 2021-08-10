package fsm.pattern.custom;

import fsm.pattern.Action;
import fsm.pattern.IContext;

public class FirstAction extends Action<Object> {
    @Override
    public Object execute(IContext context) {
        System.out.println(context.lookup("name"));
        System.out.println("inside first action");
        return context;
    }

    @Override
    public Boolean isApplicable(IContext context) {
        return true;
    }
}
