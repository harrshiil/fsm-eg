package fsm.pattern;

import java.io.Serializable;

/**
 * {@link com.elementfleet.ordering3.fsm.IFSMActionGroupResult} represents a
 * result of {@link com.elementfleet.ordering3.fsm.IFSMActionGroup} execution.
 * The result is populated during action execution and is a best known snapshot
 * at any point of time. The result can be inspected by various FSM components
 * to determine the next course of action.
 * 
 * @author Mike Yushchenko
 *
 */
public interface IFSMActionGroupResult extends Serializable {

    /**
     * @return An <code>int<code> representing total number of actions in the action
     *         group
     */
    int getTotalCount();

    /**
     * @return An <code>int<code> representing total number of successfully
     *         completed (determined by
     *         {@link com.elementfleet.ordering3.ActionFailPolicy.FailPolicy}) actions in the
     *         action group
     */
    int getSuccessCount();

    /**
     * @return An <code>int<code> representing total number of failed (determined by
     *         {@link com.elementfleet.ordering3.ActionFailPolicy.FailPolicy}) actions in the
     *         action group
     */
    int getFailureCount();

    /**
     * @return An <code>int<code> representing number of currently running actions
     *         in the action group
     */
    int getRunningCount();

    /**
     * @return An <code>int<code> representing number of skipped actions, for
     *         example as a result of cancellation, in the action group
     */
    int getSkipCount();
    
    default boolean isCompleted() {
        return getTotalCount() == getSuccessCount() + getFailureCount() + getSkipCount();
    }

}
