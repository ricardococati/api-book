package com.ricardococati.apibook.usecases.impl;

import com.ricardococati.apibook.gateways.BookGateway;
import com.ricardococati.apibook.gateways.converter.BookConverter;
import com.ricardococati.apibook.usecases.FindExternalBook;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FindExternalBookImpl implements FindExternalBook {

  private final BookGateway bookGateway;
  private static final String END_URL_PATTERN = "^.*(\\/dp\\/)([\\w+]*)(\\/?.*)?";

  @Override
  public List<BookConverter> findBookByURL() {
    List<BookConverter> bookConverterList = new ArrayList<>();
    List<String> listTitle = new ArrayList<>();
    List<String> listDescription = new ArrayList<>();
    List<String> listLanguage = new ArrayList<>();
    List<String> listISBN = new ArrayList<>();
    try {
      Document document = bookGateway.findByURL("https://kotlinlang.org/docs/books.html");
      Elements elementsByTag =
          Optional.ofNullable(document)
              .orElseThrow(RuntimeException::new)
              .getElementsByTag("article");
      for (Element element : elementsByTag) {
        listTitle.addAll(getElementTitle(element));
        listLanguage.addAll(getElementLanguage(element));
        listDescription.addAll(getElementDescription(element));
        listISBN.addAll(getElementIsbn(element));
      }
    } catch (IOException e) {
      log.debug("Error on connect URL");
    }
    return bookConverterList;
  }

  private List<String> getElementDescription(Element element) {
    Elements elementsP = element.getElementsByTag("p");
    List<String> listDescription = new ArrayList<>();
    for (Element elementP : elementsP) {
      listDescription.add(elementP.text());
    }
    return listDescription;
  }

  private List<String> getElementLanguage(Element element) {
    Elements elementsDiv = element.getElementsByClass("event-lang");
    List<String> listLanguage = new ArrayList<>();
    for (Element elementDiv : elementsDiv) {
      if (StringUtils.isNotEmpty(elementDiv.text())) {
        listLanguage.add(elementDiv.text());
      }
    }
    return listLanguage;
  }

  private List<String> getElementTitle(Element element) {
    Elements elementsH2 = element.getElementsByTag("h2");
    List<String> listTitle = new ArrayList<>();
    for (Element elementH2 : elementsH2) {
      if (StringUtils.isNotEmpty(elementH2.text())) {
        listTitle.add(elementH2.text());
      }
    }
    return listTitle;
  }

  private List<String> getElementIsbn(Element element) {
    Elements elementsH2 = element.getElementsByTag("a");
    List<String> listTitle = new ArrayList<>();
    for (Element elementH2 : elementsH2) {
      if (StringUtils.isNotEmpty(elementH2.toString())) {
        listTitle.add(elementH2.toString());
        String s = elementH2.toString();
        Matcher m = Pattern.compile(END_URL_PATTERN).matcher(s);
        if (m.matches()) {
          System.out.println("1: " + m.group(2));
        }
      }
    }
    return listTitle;
  }
}
