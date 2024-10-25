package edu.co.arsw.gridmaster.persistance;

public class ColorNotFoundException extends Exception{

    public ColorNotFoundException(String message) {
        super(message);
    }

    public ColorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}