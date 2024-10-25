package edu.co.arsw.gridmaster.model.exceptions;

public class ColorNotFoundException extends GridMasterException {
    public ColorNotFoundException(String color) {
        super("The color: '" + color + "' was not found.");
    }
}

