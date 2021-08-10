package fsm.pattern;

/**
 * ActionFailPolicy determines when an action shall be considered as failed
 *
 * @author Mike Yushchenko
 */
public enum ActionFailPolicy implements IFSMActionPolicy {

    /**
     * Never fail
     */
    FAIL_NEVER {
        @Override
        public Boolean evaluate(IFSMAction<?> action, IContext context) {
            return Boolean.FALSE;
        }

    },
    /**
     * Fail when result of the action is an exception
     */
    FAIL_ON_EXCEPTION {
        @Override
        public Boolean evaluate(IFSMAction<?> action, IContext context) {
            Object result = context.lookup(action.getGUID());
            return result == null || result instanceof Exception;
        }
    },
    /**
     * Fail when the result of the action is TRUE
     */
    FAIL_ON_FALSE {
        @Override
        public Boolean evaluate(IFSMAction<?> action, IContext context) {
            Object result = context.lookup(action.getGUID());
            return result == null || (result instanceof Boolean && !(Boolean) result);
        }
    };


}
