package fsm.pattern;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link com.elementfleet.ordering3.fsm.IFSMActionGroup} represents a group of
 * {@link com.elementfleet.ordering3.fsm.IFSMAction} that can be executed as one.
 * {@link com.elementfleet.ordering3.fsm.IFSMActionGroup} may represent either
 * asynchronous or synchronous execution.
 *
 * @author Mike Yushchenko
 */
public interface IFSMActionGroup extends IFSMAction<IFSMActionGroupResult> {

    /**
     * @return a collection of {@link com.elementfleet.ordering3.fsm.IFSMAction} that
     * contains all actions within this group
     */
    Collection<IFSMAction<?>> getActions(); // NOSONAR

    /**
     * @return a result of this action group execution
     */
    IFSMActionGroupResult getResult();

    /**
     * Returns this action WaitPolicy, by default WAIT_ALL
     *
     * @return WaitPolicy applicable to this action
     */
    default WaitPolicy getWaitPolicy() {
        return WaitPolicy.WAIT_ALL;
    }

    /**
     * Returns this action CancelPolicy, by default CANCEL_NONE
     *
     * @return CancelPolicy applicable to this action
     */
    default CancelPolicy getCancelPolicy() {
        return CancelPolicy.CANCEL_NONE;
    }

    /**
     * Returns action results.
     *
     * @param context shared context
     * @return Map of action ids to their return values (if present)
     */
    default Map<String, Object> getActionResults(IContext context) {
        Map<String, Object> r = new HashMap<>();
        getActions().forEach(action -> {
            String key = action.getGUID();
            Object actionResult = context.lookup(key);
            if (null != actionResult) {
                r.put(key, actionResult);
            }
        });
        return r;
    }
}
