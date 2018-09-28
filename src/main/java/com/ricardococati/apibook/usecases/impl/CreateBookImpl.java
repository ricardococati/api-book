package com.ricardococati.apibook.usecases.impl;

import static java.util.Objects.nonNull;

import com.ricardococati.apibook.domains.Book;
import com.ricardococati.apibook.gateways.BookGateway;
import com.ricardococati.apibook.usecases.CreateBook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateBookImpl implements CreateBook {

  private final BookGateway bookGateway;

  @Override
  public Boolean createBook(Book book) {
    return saveBook(book);
  }

  private Boolean saveBook(Book book) {
    Boolean returned = false;
    if (!existsBook(book)) {
      returned = nonNull(bookGateway.save(book));
    }
    return returned;
  }

  private boolean existsBook(Book book) {
    return bookGateway.existsBookByIsbn(book.getIsbn());
  }
}
