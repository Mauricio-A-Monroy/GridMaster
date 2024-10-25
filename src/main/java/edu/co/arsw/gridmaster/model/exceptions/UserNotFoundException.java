package edu.co.arsw.gridmaster.model.exceptions;

public class UserNotFoundException extends GridMasterException {
    public UserNotFoundException() {
        super("User not found.");
    }
}
