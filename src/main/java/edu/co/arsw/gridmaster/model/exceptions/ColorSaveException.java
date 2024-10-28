package edu.co.arsw.gridmaster.model.exceptions;

public class ColorSaveException extends ColorPersistanceException {
    public ColorSaveException() {
        super("Error saving the color.");
    }
}
