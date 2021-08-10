package fsm.pattern;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class FiniteStateMachine implements IStateMachine {

//    private static final Logger LOGGER = LoggerFactory.getLogger(FiniteStateMachine.class);

    static final String INITIAL_STATE = "INITIAL_STATE";
    static final String END_STATE = "END_STATE";

    private Map<IState, Collection<IStateTransition>> transitions;
    private Map<IState, Collection<IGuard>> preStateGuards;
    private Map<IState, Collection<IGuard>> postStateGuards;
    private Map<IState, Collection<IStateListener>> stateListeners;
    private Map<IEventType, Collection<IEventListener>> eventListeners;

    private Collection<IStateMachineListener> stateMachineListeners;

    private IContext context;
    private IState currentState;

    private String guid;

    public FiniteStateMachine(IState state) {
        Objects.requireNonNull(state, "FSM requires non-null state");
        this.guid = "FSM-" + UUID.randomUUID().toString();
        this.context = new BaseContext();
        this.transitions = new WeakHashMap<>();
        this.eventListeners = new WeakHashMap<>();
        this.stateListeners = new WeakHashMap<>();
        this.preStateGuards = new WeakHashMap<>();
        this.postStateGuards = new WeakHashMap<>();
        this.stateMachineListeners = new ArrayList<>();
        setStatePrivate(state);
    }

    public FiniteStateMachine(IState state, IContext context) {
        this(state);
        Objects.requireNonNull(context, "FSM requires non-null context");
        this.context = context;
    }

    @Override
    public IState getState() {
        return currentState;
    }

    @Override
    public Set<IState> initialStates() {
        return getStates().stream().filter(IState::isInitialState).collect(Collectors.toSet());
    }

    @Override
    public Set<IState> finalStates() {
        return getStates().stream().filter(IState::isFinalState).collect(Collectors.toSet());
    }

    @Override
    public IState onEvent(IEvent event) {
        IStateTransition transition;
        IState targetState;
        try {
            // state machine listeners
            runStateMachineListeners(event, this.getContext(), true);
            // on event listeners
            runEventListeners(event, this.getContext(), true);
            // determine possible state transition - one transition or none per
            // event type
            Optional<IStateTransition> optionalTransition = getStateTransition(this.getState(), event.getEventType());
            // if there is no state transition, return sourceState
            if (!optionalTransition.isPresent()) {
                return this.getState();
            }

            // after state listeners
            runStateListeners(this.getState(), this.getContext(), false);
            // evaluate postState guards and return if any of them fails
            if (!evaluateGuards(this.getState(), false)) {
                return this.getState();
            }
            transition = optionalTransition.get();
            // beforeTransition listeners
            runStateTransitionListeners(transition, this.getContext(), true);
            // execute transition actions
            for (IFSMAction<?> action : transition.getActions()) {
                executeActionListeners(action, true);
                try { //NOSONAR
                    action.run(this.getContext());
                } catch (Exception e) { //NOSONAR
//					LOGGER.error(
//							"################ UnExpected Error for event type {} payload {} context {} message {} - {}",
//							event.getEventType(), event.getPayload(), this.getContext(), e.getMessage(), ExceptionUtils.getStackTrace(e));
                    throw e;
                }
                executeActionListeners(action, false);
            }
            // afterTransition listeners
            runStateTransitionListeners(transition, this.getContext(), false);
            targetState = transition.getFinalState();
            // evaluate preState guards and return if any of them fails
            if (!evaluateGuards(targetState, true)) {
                return this.getState();
            }
            // execute before state listeners
            runStateListeners(targetState, this.getContext(), true);

            // after event listeners
            runEventListeners(event, this.getContext(), false);

        } catch (FailPolicyException fpe) {
            throw fpe;
        } catch (FSMException e) {
//            LOGGER.error(e.getMessage());
            return this.getState();
        } finally {
            // state machine Listeners
            runStateMachineListeners(event, this.getContext(), false);
        }
        return transition.getFinalState();
    }

    @Override
    public IContext getContext() {
        return context;
    }

    public void setState(IState state) {
        if (!transitions.containsKey(state)) {
            throw new FSMException("State machine can not add not allowed state " + state.getName());
        }
        this.currentState = state;
    }

    protected void runStateMachineListeners(final IEvent event, final IContext context, boolean onEntry) {
        for (IStateMachineListener listener : getStateMachineListeners()) {
            if (onEntry) {
                listener.onEntry(event, context);
            } else {
                listener.onExit(event, context);
            }
        }
    }

    protected void runEventListeners(final IEvent event, final IContext context, boolean onEntry) {
        for (IEventListener listener : getEventListeners(event.getEventType())) {
            if (onEntry) {
                listener.onEvent(event, context);
            } else {
                listener.afterEvent(event, context);
            }
        }
    }

    protected void runStateListeners(final IState state, final IContext context, boolean onEntry) {
        for (IStateListener listener : getStateListeners(state)) {
            if (onEntry) {
                listener.onEntry(state, context);
            } else {
                listener.onExit(state, context);
            }
        }
    }

    protected void runStateTransitionListeners(final IStateTransition transition, final IContext context, boolean onEntry) {
        for (IStateTransitionListener listener : getStateTransitionListeners(transition)) {
            if (onEntry) {
                listener.onEntry(transition, context);
            } else {
                listener.onExit(transition, context);
            }
        }
    }

    private void setStatePrivate(IState state) {
        this.currentState = state;
    }

    protected Set<IState> getStates() {
        return transitions.keySet();
    }

    protected Collection<IStateTransition> getStateTransitions() {
        return transitions.entrySet().stream().flatMap(entry -> entry.getValue().stream()).collect(Collectors.toList());
    }

    protected Collection<IStateTransition> getStateTransitionsByState(IState state) {
        return transitions.get(state);
    }

    protected Collection<IEventListener> getEventListeners(IEventType eventType) {
        return eventListeners.getOrDefault(eventType, Collections.emptyList());
    }

    protected Collection<IStateListener> getStateListeners(IState state) {
        return stateListeners.getOrDefault(state, Collections.emptyList());
    }

    protected Collection<IStateTransitionListener> getStateTransitionListeners(IStateTransition stateTransition) {
        return stateTransition.getListeners();
    }

    protected Collection<IGuard> getPreStateGuards(IState state) {
        return preStateGuards.getOrDefault(state, Collections.emptyList());
    }

    protected Collection<IGuard> getPostStateGuards(IState state) {
        return postStateGuards.getOrDefault(state, Collections.emptyList());
    }

    protected Collection<IStateMachineListener> getStateMachineListeners() {
        return stateMachineListeners;
    }

    protected Boolean evaluateGuards(IState state, boolean isPreGuard) {
        boolean result = true;
        Collection<IGuard> guards = isPreGuard ? getPreStateGuards(state) : getPostStateGuards(state);
        for (IGuard guard : guards) {
            result = guard.evaluate(this.getContext()) && result;
        }
        return result;
    }

    protected Boolean hasState(IState state) {
        return null != transitions.get(state);
    }

    protected Collection<IStateTransition> getStateTransitions(IEventType eventType) {
        return getStateTransitions().stream().filter(transition -> transition.getTriggerEventTypes().contains(eventType))
                .collect(Collectors.toList());
    }

    public Optional<IStateTransition> getStateTransition(IState state, IEventType eventType) {
        if (!hasState(state)) {
            throw new FSMException("This state machine does not allow state :" + state.getName());
        }
        return transitions.
                get(state).
                stream().
                filter(t ->
                        t.
                                getTriggerEventTypes().
                                contains(eventType)).
                findFirst();
    }

    protected void addState(IState state) {
        Objects.requireNonNull(state, "State can not be null");
        if (transitions.containsKey(state)) {
            throw new FSMException("State machine can not add the same state more than once " + state.getName());
        }
        this.transitions.put(state, new ArrayList<>());
        this.preStateGuards.put(state, new ArrayList<>());
        this.postStateGuards.put(state, new ArrayList<>());
    }

    protected void addStateTransition(IState state, IStateTransition transition) {
        Objects.requireNonNull(state, "State machine addStateTransition requires not-null state");
        Objects.requireNonNull(transition, "State machine addStateTransition requires not-null transition");
        // state must be valid
        if (!hasState(state)) {
            throw new FSMException("This state machine does not allow state :" + state.getName());
        }
        // initial state must be valid
        if (!state.equals(transition.getInitialState())) {
            throw new FSMException("Transition :" + transition.getInitialState().getName() + " does not match state :"
                    + state.getName());
        }
        // final state must be valid
        if (!hasState(transition.getFinalState())) {
            throw new FSMException("invalid state transition final state :" + transition.getFinalState());
        }
        transitions.get(state).add(transition);
    }

    protected void addEventListener(final IEventType eventType, final IEventListener listener) {
        addListener(this.eventListeners, eventType, listener);
    }

    protected void addStateListener(final IState state, final IStateListener listener) {
        addListener(this.stateListeners, state, listener);
    }

    protected void addStateTransitionListener(final StateTransition transition,
                                              final IStateTransitionListener listener) {
        transition.addListener(listener);
    }

    protected void addPreStateGuard(final IState state, final IGuard guard) {
        addStateGuard(state, guard, true);
    }

    protected void addPostStateGuard(final IState state, final IGuard guard) {
        addStateGuard(state, guard, false);
    }

    protected void addStateMachineListener(final IStateMachineListener listener) {
        this.stateMachineListeners.add(listener);
    }

    protected static <K, V> void addListener(Map<K, Collection<V>> map, K key, V listener) {
        Objects.requireNonNull(listener, "Listener can not be null");
        Objects.requireNonNull(key, "Listener key can not be null");
        if (!map.containsKey(key)) {
            map.put(key, new ArrayList<>());
        }
        map.get(key).add(listener);
    }

    protected FiniteStateMachine lock() {
        this.transitions = lockMapOfCollections(this.transitions);
        this.preStateGuards = lockMapOfCollections(this.preStateGuards);
        this.postStateGuards = lockMapOfCollections(this.postStateGuards);
        this.stateListeners = lockMapOfCollections(this.stateListeners);
        this.eventListeners = lockMapOfCollections(this.eventListeners);
        this.stateMachineListeners = Collections.unmodifiableCollection(this.stateMachineListeners);
        return this;
    }

    protected static <K, V> Map<K, Collection<V>> lockMapOfCollections(Map<K, Collection<V>> map) {
        for (Entry<K, Collection<V>> entry : map.entrySet()) {
            map.put(entry.getKey(), Collections.unmodifiableCollection(map.get(entry.getKey())));
        }
        return Collections.unmodifiableMap(new LinkedHashMap<>(map));
    }

    private void addStateGuard(final IState state, final IGuard guard, Boolean pre) {
        Objects.requireNonNull(state, "State can not be null");
        Objects.requireNonNull(guard, "Guard can not be null");
        if (!this.hasState(state)) {
            throw new FSMException("State machine can not add a guard to not supported state " + state.getName());
        }
        if (pre) {
            if (state.isInitialState()) {
                throw new FSMException("State machine can not add a pre-guard to initial state " + state.getName());
            }
            this.preStateGuards.get(state).add(guard);
        } else {
            if (state.isFinalState()) {
                throw new FSMException("State machine can not add a post-guard to final state " + state.getName());
            }
            this.postStateGuards.get(state).add(guard);
        }
    }

    private void executeActionListeners(IFSMAction<?> action, boolean onEntry) {
        for (IActionListener listener : action.getListeners()) {
            if (onEntry) {
                listener.onEntry(action, this.getContext());
            } else {
                listener.onExit(action, this.getContext());
            }
        }
    }

    @Override
    public String getGUID() {
        return guid;
    }

}
