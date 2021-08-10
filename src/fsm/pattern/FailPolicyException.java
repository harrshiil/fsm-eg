package fsm.pattern;

public class FailPolicyException extends FSMException {

    private static final long serialVersionUID = 4845388627683779167L;

    public FailPolicyException(String message) {
        super(message);
    }

    public FailPolicyException(String message, Throwable cause) {
        super(message, cause);
    }
}
