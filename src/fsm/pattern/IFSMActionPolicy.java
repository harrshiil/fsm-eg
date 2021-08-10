package fsm.pattern;

/**
 * FSM components, such as listeners may evaluate actions and context
 * against policies, for example to determine how action groups shall fail or
 * pass.
 * 
 * @author Mike Yushchenko
 *
 */
public interface IFSMActionPolicy {

    /**
     * @param actionGroup
     *            An {@link com.elementfleet.ordering3.fsm.IFSMAction} to be
     *            evaluated
     * @param context
     *            An {@link com.elementfleet.ordering3.fsm.IContext} to evaluate
     *            action against
     * @return <code>Boolean</code> value representing the result of policy
     *         evaluation
     */
    Boolean evaluate(IFSMAction<?> action, IContext context);
    
    
    
}
