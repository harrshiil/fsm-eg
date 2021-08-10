package fsm.pattern;

/**
 * Determines when the action group is considered to fail
 * 
 * @author Mike Yushchenko
 *
 */
public enum GroupFailPolicy implements IFSMActionPolicy {

    /*
     * Never fails
     */
    FAIL_NEVER {
        @Override
        public Boolean evaluate(IFSMAction<?> actionGroup, IContext context) {
            validate(actionGroup);
            return Boolean.FALSE;
        }
    },
    /*
     * Fails on any action failure
     */
    FAIL_ON_ANY_FAILURE {
        @Override
        public Boolean evaluate(IFSMAction<?> actionGroup, IContext context) {
            validate(actionGroup);
            ActionGroupResult result = (ActionGroupResult) context.lookup(actionGroup.getGUID());
            return null == result || result.getFailureCount() > 0;
        }
    },

    /*
     * Fails on any skipped action
     */
    FAIL_ON_ANY_SKIPPED {
        @Override
        public Boolean evaluate(IFSMAction<?> actionGroup, IContext context) {
            validate(actionGroup);
            ActionGroupResult result = (ActionGroupResult) context.lookup(actionGroup.getGUID());
            return null == result || result.getSkipCount() > 0;
        }
    },

    /*
     * Fails when no successful actions
     */
    FAIL_ON_NO_SUCCESS {
        @Override
        public Boolean evaluate(IFSMAction<?> actionGroup, IContext context) {
            validate(actionGroup);
            ActionGroupResult result = (ActionGroupResult) context.lookup(actionGroup.getGUID());
            return null == result || result.getSuccessCount() == 0;
        }
    };
    
    private static void validate(IFSMAction<?> action) {
        if(!(action instanceof ActionGroup)) {
            throw new FSMException("Action " + action.getName() + " is not ActionGroup");
        }
    }
}
