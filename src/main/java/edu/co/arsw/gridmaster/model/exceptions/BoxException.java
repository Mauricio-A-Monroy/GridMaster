package edu.co.arsw.gridmaster.model.exceptions;

public class BoxException extends GridMasterException {
  public BoxException() {
    super();
  }

  public BoxException(String message) {
    super(message);
  }

  public BoxException(String message, Throwable cause) {
    super(message, cause);
  }
}