package com.ricardococati.apibook.usecases.impl;

import com.ricardococati.apibook.domains.Book;
import com.ricardococati.apibook.exceptions.ValidationException;
import com.ricardococati.apibook.gateways.BookGateway;
import com.ricardococati.apibook.gateways.converter.BookConverter;
import com.ricardococati.apibook.usecases.FindBook;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FindBookImpl implements FindBook {

  private final BookGateway bookGateway;

  @Override
  public BookConverter findByIdBook(String idBook) throws ValidationException {
    return BookConverter.bookConverter(bookGateway.findById(idBook));
  }

  @Override
  public List<BookConverter> findAll() {
    return bookGateway
        .findAll()
        .stream()
        .filter(Objects::nonNull)
        .map(book -> BookConverter.bookConverter(book))
        .collect(Collectors.toList());
  }
}
