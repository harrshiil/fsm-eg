package fsm.pattern;

import java.time.Duration;
import java.util.*;

public abstract class Action<T> implements IFSMAction<T> {

    public static final String GUID_PREFIX = "ACTION-";

    protected Set<IActionListener> listeners;
    private String id;
    protected Duration timeout;

    protected Set<IFSMActionPolicy> failPolicies;

    protected Action() {
        this.listeners = new LinkedHashSet<>();
        this.id = GUID_PREFIX + UUID.randomUUID().toString();
        this.timeout = Duration.ofSeconds(Integer.MAX_VALUE);
        this.failPolicies = new LinkedHashSet<>(Arrays.asList(ActionFailPolicy.FAIL_ON_EXCEPTION));
    }

    /**
     * Returns this action FailPolicies, by default FAIL_ON_EXCEPTION
     *
     * @return Collection of FailPolicies applicable to this action
     */

    public Set<IFSMActionPolicy> getFailPolicies() {
        return this.failPolicies;
    }


    public Action<T> setFailPolicies(IFSMActionPolicy... policies) {
        this.failPolicies = new LinkedHashSet<>();
        for (IFSMActionPolicy policy : policies) {
            this.failPolicies.add((ActionFailPolicy) policy);
        }
        if (this.failPolicies.size() > 1 && this.failPolicies.contains(ActionFailPolicy.FAIL_NEVER)) {
            this.failPolicies.remove(ActionFailPolicy.FAIL_NEVER);
        }
        if (this.failPolicies.isEmpty()) {
            failPolicies.add(ActionFailPolicy.FAIL_NEVER);
        }
        return this;
    }

    /**
     * Adds an action listener to this action.
     *
     * @param listener IActionListener to add to this action
     * @return An instance of this action
     */
    @Override
    public Action<T> addListener(final IActionListener listener) {
        this.listeners.add(Objects.requireNonNull(listener, "listener must not be null"));
        return this;
    }

    /**
     * Provides access to IActionListeners registered with this IFSMAction.
     *
     * @return A Collection of IActionListeners
     */
    @Override
    public Set<IActionListener> getListeners() {
        return this.listeners;
    }

    /**
     * Action's GUID is generated using a standard JDK UUID.randomUUID()
     *
     * @return String value of id
     */
    @Override
    public String getGUID() {
        return this.id;
    }

    /**
     * Defines a timeout after which the running action shall time out when executed
     * asynchronously. This property has no effect on blocking actions. By default,
     * is Integer.MAX_VALUE ms.
     *
     * @return Duration of timeout
     */
    @Override
    public Duration getTimeout() {
        return this.timeout;
    }

    /**
     * Set the timeout that will be used for asynchronous action executions, not
     * used when action is executed synchronously.
     *
     * @param timeout Duration after which action shall time out if not completed
     * @return An instance of this actions
     */
    @Override
    public Action<T> setTimeout(Duration duration) {
        this.timeout = Objects.requireNonNull(duration, "duration is required for timeout");
        return this;
    }

    @Override
    public void run(IContext context) {
        if (isApplicable(context)) {
            new ActionSupplier<T>(this, context).get();
//            SecurityContext.createEmptySecurityAttributes();
//            SecurityContextHelper.setSecurityAttributes(context);
        }
    }

    @Override
    public String toString() {
        return "Action{" +
                "listeners=" + listeners +
                ", id='" + id + '\'' +
                ", timeout=" + timeout +
                ", failPolicies=" + failPolicies +
                '}';
    }
}