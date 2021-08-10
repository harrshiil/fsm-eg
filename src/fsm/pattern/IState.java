package fsm.pattern;

/**
 * {@link com.elementfleet.ordering3.fsm.IState} is an interface representing a
 * possible state of FSM.
 *
 * @author Mike Yushchenko
 */
public interface IState {

    String getName();

    /**
     * By default, states are not final. No transitions are allowed from final
     * states.
     * 
     * @return <code>Boolean</code> representing if this state is a final state
     */
    default Boolean isFinalState() {
        return false;
    }

    /**
     * By default, states are not initial. No transitions are allowed to initial
     * states.
     * 
     * @return <code>Boolean</code> representing if this state is initial state
     */
    default Boolean isInitialState() {
        return false;
    }

}
