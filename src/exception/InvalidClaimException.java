package exception;

import config.AppConfig;

public class InvalidClaimException extends RuntimeException {
    public InvalidClaimException(String message) {
        super("Claim Amount is not in range 0 to "+ AppConfig.INSTANCE.maximumClaimAmount());
    }
}
