package exception;

public class UnknownPolicyTypeException extends RuntimeException {
    public UnknownPolicyTypeException(String message) {
        super(message);
    }
}
