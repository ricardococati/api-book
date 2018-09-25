package com.ricardococati.api-book.exceptions;

public class ValidationException extends Exception {

  public ValidationException(String message, Exception exc){
    super(message, exc);
  }

  public ValidationException(String message){
    super(message);
  }

}
