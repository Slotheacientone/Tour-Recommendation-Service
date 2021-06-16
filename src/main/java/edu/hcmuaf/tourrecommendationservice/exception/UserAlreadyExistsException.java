package edu.hcmuaf.tourrecommendationservice.exception;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
