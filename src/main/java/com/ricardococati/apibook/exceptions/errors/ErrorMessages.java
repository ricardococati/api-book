package com.ricardococati.apibook.exceptions.errors;

import lombok.Getter;

public enum ErrorMessages {

  ERROR_SEQUENCE_NOT_NULL("Could not to sent dna sequence null!"),
  ERROR_VERIFY_DNA("Error to verify Dna!"),
  ERROR_ON_VALIDATE_DNA_SEQUENCE("Error on validate Dna Sequence!!"),
  ERROR_DNA_RANGE_INVALID("Dna range is invalid for test");

  @Getter
  private String message;

  ErrorMessages(String message){
    this.message = message;
  }

}
