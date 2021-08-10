package fsm.pattern;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

/*
 * Executes an action in a given context and binds result (or exception) to the
 * context. Effectively converts the action into a Supplier that can be passed
 * to a CompletableFuture or just implement Decorator pattern
 */
public class ActionSupplier<T> implements Supplier<T> {

//    private static final Logger LOGGER = LoggerFactory.getLogger(ActionSupplier.class);

    public static final String CORRELATION_ID_KEY = "CORRELATION_ID";
    public static final String USER_ID_KEY = "USER_ID";
    public static final String USER_TOKEN_KEY = "USER_TOKEN";
    public static final String PROGRAM_KEY = "PROGRAM";

    private IFSMAction<T> action;
    private IContext context;

    public ActionSupplier(final IFSMAction<T> action, final IContext context) {
        this.action = Objects.requireNonNull(action, "Action must not be null");
        this.context = Objects.requireNonNull(context, "Context must not be null");
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get() {
        try {
//        LOGGER.debug("Entering ActionSupplier get() for action {}", action);
//        SecurityContextHelper.setSecurityAttributes(context);
//        LOGGER.debug("On entry {}", SecurityContext.getSecurityAttributes().toString());
//        LOGGER.debug("Invoking pre-action listeners for action {}", action);
        invokeActionListeners(true);
//        LOGGER.debug("Storing empty result in the context before execution of action {}", action);
        context.bind(action.getGUID(), Optional.empty());
        Object result  = null;
        try {
//            LOGGER.debug("Invoking execute() for action {}", action);
            result = action.execute(context);
//            LOGGER.debug("Storing result {} in the context after execution of action {}", result, action);
            context.bind(action.getGUID(), result);
        } catch (Exception e) {
        	logMessage(e);
            Optional<T> defaultValue = action.getDefaultValue(context);
            if (defaultValue.isPresent()) {
//                LOGGER.debug("Storing default value {} in the context instead of exception for action {}", defaultValue,
//                    action);
                result = defaultValue.get();
                context.bind(action.getGUID(), defaultValue.get());
            } else {
//                LOGGER.debug("No default value , storing an exception for action {}", action);
                context.bind(action.getGUID(), e);
                result = e;
            }
        }
        if (null == result) {
//            LOGGER.warn("No result for action {}, will throw FSMException", action.toString());
            FSMException fsme =  new FSMException("Action " + action.getName() + " produced no result");
            context.bind(action.getGUID(), fsme);
            throw fsme;
        }
        validateResult(result);
//        LOGGER.debug("Invoking post-action listeners for action {}", action);
        invokeActionListeners(false);
//        LOGGER.debug("Exiting ActionSupplier get() for action {}", action);
        return (T)result;
        }
        finally {
//        	SecurityContextHelper.clearSecurityAttributes();
//        	LOGGER.debug("On exit {}", SecurityContext.getSecurityAttributes());
         }
    }
    


	private void logMessage(Exception e) {
//		final String message = String.format("%s action failed with message %s", action.getName(), e.getMessage());
//		if (e instanceof OrderingException) {
//			switch (((OrderingException) e).getOrderingExceptionMessage().getType()) {
//			case ERROR:
////				LOGGER.error(message, e);
//				break;
//			case INFO:
////				LOGGER.info(message);
//				break;
//			default:
////				LOGGER.warn(message);
//			}
//		} else {
////			LOGGER.error(message, e);
//		}
	}

	private void validateResult(Object result) {
        Set<IFSMActionPolicy> failPolicies = action.getFailPolicies();
        for (IFSMActionPolicy fp : failPolicies) {
            if (fp.evaluate(action, context)) {
                FailPolicyException fpe = null;
                if (result instanceof Throwable) {
//                    LOGGER.debug("Action {} produced Throwable {}", action, result);
                    fpe = new FailPolicyException("Failed policy: " + fp.toString(), (Throwable) result);
                } else {
//                    LOGGER.debug("Action {} failed FailPolicy {}", action, fp);
                    fpe =  new FailPolicyException(
                        "Failed policy: " + fp.toString() + " with result = " + result.toString());
                }
                context.bind(action.getGUID(), fpe);
                throw fpe;
            }
        }
    }

    private void invokeActionListeners(boolean onEntry) {
        if (action.isApplicable(context)) {
            for (IActionListener listener : action.getListeners()) {
                if (onEntry) {
                    listener.onEntry(action, context);
                } else {
                    listener.onExit(action, context);
                }
            }
        }

    }

}