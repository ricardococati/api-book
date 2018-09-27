package com.ricardococati.apibook.gateways.repository;

import com.ricardococati.apibook.domains.Book;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {

  boolean existsByIsbn(Long isbn);

  Optional<Book> findById(String idBook);
}
