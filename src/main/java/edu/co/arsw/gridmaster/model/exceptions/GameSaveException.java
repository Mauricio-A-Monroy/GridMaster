package edu.co.arsw.gridmaster.model.exceptions;

public class GameSaveException extends GridMasterException {
    public GameSaveException() {
        super("Unable to save the game.");
    }
}

