package com.ricardococati.apibook.gateways.converter;

import static java.util.Objects.nonNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ricardococati.apibook.domains.Book;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookConverter {

  @JsonProperty("id")
  private String idBook;
  @JsonProperty("title")
  private String title;
  @JsonProperty("description")
  private String description;
  @JsonProperty("ISBN")
  private Long isbn;
  @JsonProperty("language")
  private String language;

  public static BookConverter bookConverter(Book book) {
    return BookConverter.builder()
        .idBook(book.getIdBook())
        .description(book.getDescription())
        .title(book.getTitle())
        .isbn(book.getIsbn())
        .language(book.getLanguage())
        .build();
  }

  public static Book bookConverter(BookConverter bookConverter) {
    return Book.builder()
        .description(bookConverter.getDescription())
        .title(bookConverter.getTitle())
        .isbn(bookConverter.getIsbn())
        .language(bookConverter.getLanguage())
        .build();
  }

}
