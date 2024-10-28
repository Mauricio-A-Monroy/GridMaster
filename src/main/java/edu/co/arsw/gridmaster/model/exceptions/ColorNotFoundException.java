package edu.co.arsw.gridmaster.model.exceptions;

public class ColorNotFoundException extends ColorPersistanceException {
    public ColorNotFoundException() {
        super("The color was not found.");
    }
}

