package fsm.pattern;

/**
 * Defines possible cancellation policies for {@link com.elementfleet.ordering3.fsm.IFSMAction}
 * @author Mike Yushchenko
 *
 */
public enum CancelPolicy implements IFSMActionGroupPolicy {

    /**
     * Let actions naturally complete or time out.
     *
     */
    CANCEL_NONE {
        @Override
        public Boolean evaluate(IFSMActionGroup actionGroup, IContext context) {
            return Boolean.FALSE;
        }
        
    }, 
    
    /**
     * Cancel only the current action, do not cancel other actions
     *
     */
    CANCEL_ONE {
        @Override
        public Boolean evaluate(IFSMActionGroup actionGroup, IContext context) {
            return Boolean.TRUE;
        }
        
    }, 
    /**
     * Do not cancel the current thread, cancel other actions in the group
     *
     */
    CANCEL_ALL {
        @Override
        public Boolean evaluate(IFSMActionGroup actionGroup, IContext context) {
            return Boolean.TRUE;
        }
    };

}
