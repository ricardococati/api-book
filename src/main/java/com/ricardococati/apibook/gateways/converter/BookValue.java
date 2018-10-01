package com.ricardococati.apibook.gateways.converter;

import lombok.Data;

@Data
public class BookValue {

  private String title;
  private String language;
  private String isbn;
  private String description;

}
