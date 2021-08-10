package fsm.pattern;

public class FSMException extends RuntimeException {

    private static final long serialVersionUID = -1670956147912137201L;

    public FSMException(String message) {
        super(message);
    }

    public FSMException(String message, Throwable cause) {
        super(message, cause);
    }
}
