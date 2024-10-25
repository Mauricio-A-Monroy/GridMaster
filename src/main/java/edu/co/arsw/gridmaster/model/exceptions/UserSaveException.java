package edu.co.arsw.gridmaster.model.exceptions;

public class UserSaveException extends GridMasterException {
    public UserSaveException() {
        super("Unable to save user.");
    }
}
