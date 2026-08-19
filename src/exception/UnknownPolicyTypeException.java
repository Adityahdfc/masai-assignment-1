package exception;

public class UnknownPolicyTypeException extends RuntimeException {
    public UnknownPolicyTypeException(){
        super("Unknown Factory Type");
    }
}
