package com.ricardococati.apibook.exceptions;

public class ValidationException extends RuntimeException {

  public ValidationException(String message, RuntimeException exc){
    super(message, exc);
  }

  public ValidationException(String message){
    super(message);
  }

  public ValidationException(){
    super();
  }

}
