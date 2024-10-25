package edu.co.arsw.gridmaster.model.exceptions;

public class GameNotFoundException extends GridMasterException {
    public GameNotFoundException() {
        super("Game not found.");
    }
}
