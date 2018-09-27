package com.ricardococati.apibook.gateways;

import com.ricardococati.apibook.domains.Book;
import java.util.List;
import java.util.Optional;

public interface BookGateway {

  Book save(Book book);

  boolean existsBookByIsbn(Long book);

  List<Book> findAll();

  Book findById(String idBook);
}
