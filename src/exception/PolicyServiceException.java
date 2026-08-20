package exception;

public class PolicyServiceException extends RuntimeException{

    public PolicyServiceException(String message,Exception cause){
        super("message");
    }
}
