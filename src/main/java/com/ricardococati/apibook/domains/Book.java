package com.ricardococati.apibook.domains;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Serializable {

  private static final long serialVersionUID = -6660019736137472637L;
  @Id
  private String idBook;
  private String title;
  private String description;
  private Long isbn;
  private String language;

}
