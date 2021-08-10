package fsm.pattern;

/**
 * {@link com.elementfleet.ordering3.fsm.impl.FiniteStateMachine} evaluates
 * state guards twice: before and after state transitions. If the guard evaluates as
 * <code>false</code> then FSM will preserve the original state for both
 * pre-state and post-state guards.
 * <p>
 * Post-state guards are evaluated right before state transition listeners and
 * pre-state guards are evaluated right after state transition listeners.
 * 
 * @author Mike Yushchenko
 *
 */
public interface IGuard {

    /**
     * 
     * Evaluates the context. This method should not have side effects.
     * 
     * @param context
     *            FSM shared context
     * @return the Boolean result of context evaluation
     */
    Boolean evaluate(IContext context);

}
