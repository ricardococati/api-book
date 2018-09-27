package com.ricardococati.apibook.usecases.impl;

import com.ricardococati.apibook.gateways.BookGateway;
import com.ricardococati.apibook.gateways.converter.BookConverter;
import com.ricardococati.apibook.usecases.FindExternalBook;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FindExternalBookImpl implements FindExternalBook {

  private final BookGateway bookGateway;

  @Override
  public List<BookConverter> findBookByURL() {
    try {
      Document document = getHTMLToJSoupDocument();
    } catch (IOException e) {
      log.debug("Error on connect URL");
    }
    return null;
  }

  private Document getHTMLToJSoupDocument() throws IOException {
    return Jsoup.connect("https://kotlinlang.org/docs/books.html").get();
  }
}
