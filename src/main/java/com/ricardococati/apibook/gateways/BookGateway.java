package com.ricardococati.apibook.gateways;

import com.ricardococati.apibook.domains.Book;
import java.io.IOException;
import java.util.List;
import org.jsoup.nodes.Document;

public interface BookGateway {

  Book save(Book book);

  boolean existsBookByIsbn(String book);

  List<Book> findAll();

  Book findById(String idBook);

  Document findByURL(String url) throws IOException;
}
