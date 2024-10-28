package edu.co.arsw.gridmaster.model.exceptions;

public class UserSaveException extends UserPersistanceException {
    public UserSaveException() {
        super("Unable to save user.");
    }
}
