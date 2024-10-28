package edu.co.arsw.gridmaster.model.exceptions;

public class UserPersistanceException extends GridMasterException {
    public UserPersistanceException() {
        super();
    }

    public UserPersistanceException(String message) {
        super(message);
    }

    public UserPersistanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
