package fsm.pattern;
import java.util.Set;

public interface IStateMachine extends IEventHandler<IState> { //NOSONAR

    String CURRENT_EVENT_KEY = "CURRENT_EVENT";

    IState getState();

    Set<IState> initialStates();

    Set<IState> finalStates();

    IContext getContext();

    String getGUID();

}
