package edu.co.arsw.gridmaster.model.exceptions;

public class UserNotFoundException extends UserPersistanceException {
    public UserNotFoundException() {
        super("User not found.");
    }
}
