package fsm.pattern;

public enum WaitPolicy implements IFSMActionGroupPolicy {

    WAIT_NONE {
        @Override
        public Boolean evaluate(IFSMActionGroup actionGroup, IContext context) {
            return Boolean.FALSE;
        }
    },
    WAIT_ANY {
        @Override
        public Boolean evaluate(IFSMActionGroup actionGroup, IContext context) {
            IFSMActionGroupResult result = actionGroup.getResult();
            return result.getSuccessCount() > 0 || result.getFailureCount() > 0 || result.isCompleted();
        }
    },
    WAIT_ANY_SUCCESS {
        @Override
        public Boolean evaluate(IFSMActionGroup actionGroup, IContext context) {
            IFSMActionGroupResult result = actionGroup.getResult();
            return result.getSuccessCount() > 0 || result.isCompleted();
        }
    },
    WAIT_ANY_FAILURE {
        @Override
        public Boolean evaluate(IFSMActionGroup actionGroup, IContext context) {
            IFSMActionGroupResult result = actionGroup.getResult();
            return result.getFailureCount() > 0 || result.isCompleted();
        }
    },
    WAIT_ANY_SKIPPED {
        @Override
        public Boolean evaluate(IFSMActionGroup actionGroup, IContext context) {
            IFSMActionGroupResult result = actionGroup.getResult();
            return result.getSkipCount() > 0 || result.isCompleted();
        }
    },
    WAIT_ALL {
        @Override
        public Boolean evaluate(IFSMActionGroup actionGroup, IContext context) {
            IFSMActionGroupResult result = actionGroup.getResult();
            return result.isCompleted();
        }
    };
}
