package fsm.pattern;

/**
 * {@link com.elementfleet.ordering3.fsm.IAction} is a high level interface
 * representing one unit of work.
 * {@link com.elementfleet.ordering3.fsm.impl.FiniteStateMachine} executes
 * {@link com.elementfleet.ordering3.fsm.IFSMAction}s within FSM context. Actions
 * may behave differently based on run-time context.
 * 
 * @author Mike Yushchenko
 *
 * @param <T>
 *            represents the result of action execution
 */
public interface IAction<T> {

    /**
     * One action execution that produces a result.
     * 
     * @param context
     *            Shared context
     * @return Action execution result. Should not be null.
     *         {@link com.elementfleet.ordering3.fsm.impl.FiniteStateMachine} uses
     *         action return value to determine if the action has been completed or
     *         not.
     */
    T execute(IContext context);

}
