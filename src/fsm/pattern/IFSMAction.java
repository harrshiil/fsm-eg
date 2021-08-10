package fsm.pattern;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * Represents an action that can be executed by FSM. Each Action has a unique
 * ID, name, timeout, optionally associated with listeners and may provide a
 * default value that shall be used when an exception is thrown by the run
 * method. Implementations shall implement the execute method from IAction
 * interface with specific logic, FSM will execute a wrapper run method that in
 * turn will invoke execute method. Actions may be applicable to specific
 * contexts.
 * 
 * @author Mike Yushchenko
 * @param <T>
 */
public interface IFSMAction<T> extends IAction<T> {

    /**
     * Every IFSMAction must have a globally unique id that is used as a context
     * key.
     * 
     * @return String value of id
     */
    String getGUID();

    /**
     * Set the timeout that will be used for asynchronous action executions, it is
     * not used when an action is executed synchronously.
     * 
     * @param timeout
     *            Duration after which action shall time out if not completed
     * @return An instance of this actions
     */
    IFSMAction<T> setTimeout(Duration timeout);

    /**
     * Defines a mechanism to add action listeners to actions.
     * 
     * @param listener
     *            {@link com.elementfleet.ordering3.fsm.listeners.IActionListener} to
     *            add to this action
     * @return An instance of this action
     */
    IFSMAction<T> addListener(IActionListener listener);
   
    /**
     * Defines a timeout after which the running action shall time out when executed
     * asynchronously. This property has no effect on blocking actions. By default,
     * is Integer.MAX_VALUE ms.
     * 
     * @return Duration of timeout
     */
    default Duration getTimeout() {
        return Duration.ofMillis(Integer.MAX_VALUE);
    }

    /**
     * Defines {@link com.elementfleet.ordering3.fsm.IFSMAction} name that can be
     * used as alternate identifier, for example for logging, defaults to a simple
     * action class name.
     * 
     * @return A String representing action name
     */
    default String getName() {
        return getClass().getSimpleName();
    }

    /**
     * Provides access to IActionListeners registered with this IFSMAction. By
     * default provides no access.
     * 
     * @return A Collection of IActionListeners, by default an empty list
     */
    default Collection<IActionListener> getListeners() {
        return Collections.emptyList();
    }

    /**
     * Whether or not this action is applicable to the specified context. This
     * method can be overridden to provide fine-grained conditional execution of
     * actions. For example, an action may be applicable and runs only if a certain
     * value present in the shared context.
     * 
     * @param context
     *            Shared FSM context
     * @return A Boolean that defines if an action is applicable to specific
     *         context, by default is <code>true</code>.
     */
    default Boolean isApplicable(IContext context) {
        return true;
    }

    /**
     * Optional default value that will be returned in case if this action throws an
     * exception or times out.
     * 
     * @param context
     *            Shared FSM context
     * @return An Optional that contains the value of the action result or empty. By
     *         default, it is empty.
     */
    default Optional<T> getDefaultValue(IContext context) {
        return Optional.empty();
    }
    
     Set<IFSMActionPolicy> getFailPolicies();

    /**
     * Invoked by the FSM, wraps execute method with default behavior that can be
     * overridden by implementation classes. By default, attempts to execute an
     * action if it is applicable and throws
     * {@link com.elementfleet.ordering3.fsm.exception.IFSMException} if the action
     * returns <code>null</code> result. If an action is not applicable to a given
     * context, nothing will happen. If an exception is thrown, then either it is
     * re-thrown or default value is stored in the context. This method has a side
     * effect: it stores non-empty result of action execution or exception in the
     * shared context so it can be used by other components of FSM. This method
     * stores an empty optional in the context when action execution begins and
     * updates it when execution completes.
     * 
     * @param context
     *            Shared FSM context
     * @return An Optional that contains the value of the action result or empty.
     */
    void run(IContext context);

    default boolean hasStarted(IContext context) {
        return null != context.lookup(this.getGUID());
    }
    
    default boolean isRunning(IContext context) {
        Object result = context.lookup(this.getGUID());
        return null != result && result instanceof Optional<?>;
    }
    
    default boolean hasCompleted(IContext context) {
        Object result = context.lookup(this.getGUID());
        return null != result && !(result instanceof Optional<?>);
    }
    
    
}
