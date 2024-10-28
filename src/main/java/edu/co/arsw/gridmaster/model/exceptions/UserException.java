package edu.co.arsw.gridmaster.model.exceptions;

public class UserException extends GridMasterException {
  public UserException() {
    super();
  }

  public UserException(String message) {
    super(message);
  }

  public UserException(String message, Throwable cause) {
    super(message, cause);
  }
}