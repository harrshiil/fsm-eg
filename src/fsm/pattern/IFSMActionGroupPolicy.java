package fsm.pattern;

/**
 * FSM components, such as listeners may evaluate action groups and context
 * against policies, for example to determine how action groups shall fail or
 * pass.
 * 
 * @author Mike Yushchenko
 *
 */
public interface IFSMActionGroupPolicy {

    /**
     * @param actionGroup
     *            An {@link com.elementfleet.ordering3.fsm.IFSMActionGroup} to be
     *            evaluated
     * @param context
     *            An {@link com.elementfleet.ordering3.fsm.IContext} to evaluate an
     *            action group against
     * @return <code>Boolean</code> value representing the result of policy
     *         evaluation
     */
    Boolean evaluate(IFSMActionGroup actionGroup, IContext context);

}
