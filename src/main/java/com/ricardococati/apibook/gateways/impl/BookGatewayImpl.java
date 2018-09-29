package com.ricardococati.apibook.gateways.impl;

import com.ricardococati.apibook.domains.Book;
import com.ricardococati.apibook.exceptions.ValidationException;
import com.ricardococati.apibook.gateways.BookGateway;
import com.ricardococati.apibook.gateways.repository.BookRepository;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookGatewayImpl implements BookGateway {

  private final BookRepository bookRepository;

  @Override
  public Book save(Book book) {
    return bookRepository.save(book);
  }

  @Override
  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  @Override
  public Book findById(String idBook) {
    return bookRepository
        .findById(idBook)
        .orElseThrow(() -> new ValidationException("Error on find by Id"));
  }

  @Override
  public Document findByURL(String url) throws IOException {
    return Jsoup.connect(url).get();
  }

  @Override
  public boolean existsBookByIsbn(String isbn) {
    return bookRepository.existsByIsbn(isbn);
  }
}
