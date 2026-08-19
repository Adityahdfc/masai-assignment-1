package exception;

public class PolicyNotFoundException extends RuntimeException{
    public PolicyNotFoundException(){
        super("Missing Policy Number");
    }
}
