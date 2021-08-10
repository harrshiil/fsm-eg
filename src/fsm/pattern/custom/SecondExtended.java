package fsm.pattern.custom;

import fsm.pattern.IContext;

public class SecondExtended extends SecondAction {

    @Override
    public Boolean isApplicable(IContext context) {
        if (!super.isApplicable(context)) {
            return false;
        }
        return true;
    }
}
