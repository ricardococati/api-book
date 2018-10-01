package com.ricardococati.apibook.usecases.impl;

import com.ricardococati.apibook.gateways.BookGateway;
import com.ricardococati.apibook.gateways.converter.BookConverter;
import com.ricardococati.apibook.gateways.converter.BookValue;
import com.ricardococati.apibook.usecases.FindExternalBook;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Data;
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

  private static final String TAG_NAME_ARTICLE = "article";
  private static final String URL = "https://kotlinlang.org/docs/books.html";
  private final BookGateway bookGateway;
  private static final String END_URL_PATTERN = "^.*(\\/dp\\/)([\\w+]*)(\\/?.*)?";

  @Override
  public List<BookConverter> findBookByURL() throws IOException {
    List<BookConverter> bookConverterList = new ArrayList<>();
    List<BookValue> bookValueList = new ArrayList<>();
    Document document = bookGateway.findByURL(URL);
    Elements elementsByTag =
        Optional.ofNullable(document)
            .orElseThrow(RuntimeException::new)
            .getElementsByTag(TAG_NAME_ARTICLE);

    for (Element element : elementsByTag) {
      bookValueList.addAll(getElementBooks(element));
    }
    for (BookValue bookValue: bookValueList) {
      bookConverterList.add(BookConverter.bookConverter(bookValue));
    }
    return bookConverterList;
  }

  private List<BookValue> getElementBooks(Element element) {
    Elements elementsTitle = element.getElementsByTag("h2");
    List<BookValue> bookValueList = new ArrayList<>();
    for (Element elementH2 : elementsTitle) {
      BookValue bookValue = new BookValue();
      if (StringUtils.isNotEmpty(elementH2.text()) && !bookValueList.contains(elementH2.text())) {
        bookValue.setTitle(elementH2.text());
        bookValueList.add(bookValue);
      }
    }
    bookValueList = getElementLanguage(element, bookValueList);
    bookValueList = getElementDescription(element, bookValueList);
    bookValueList = getElementIsbn(element, bookValueList);
    return bookValueList;
  }

  private List<BookValue> getElementDescription(Element element, List<BookValue> bookValueList) {
    Elements elementsP = element.getElementsByTag("p");
    for (Element elementP : elementsP) {
      for (int i = 0; i < bookValueList.size(); i++) {
        if (StringUtils.isNotEmpty(elementP.text())
            && elementP.text().contains(bookValueList.get(i).getTitle())) {
          bookValueList.get(i).setDescription(elementP.text());
        }
      }
    }
    return bookValueList;
  }

  private List<BookValue> getElementLanguage(Element element, List<BookValue> bookValueList) {
    Elements elementsDiv = element.getElementsByClass("event-lang");
    List<String> listLanguage = new ArrayList<>();
    for (Element elementDiv : elementsDiv) {
      if (StringUtils.isNotEmpty(elementDiv.text())) {
        listLanguage.add(elementDiv.text());
      }
    }
    if (bookValueList.size() == listLanguage.size()) {
      for (int i = 0; i < bookValueList.size(); i++) {
        bookValueList.get(i).setLanguage(listLanguage.get(i));
      }
    }
    return bookValueList;
  }

  private List<BookValue> getElementIsbn(Element element, List<BookValue> bookValueList) {
    Elements elementsH2 = element.getElementsByTag("a");
    List<String> listLink = new ArrayList<>();
    List<String> listLinkFinal = new ArrayList<>();
    for (int i = 0; i < bookValueList.size(); i++) {
      for (Element elementH2 : elementsH2) {
        if (elementH2.text().contains(bookValueList.get(i).getTitle())
            && !listLinkFinal.contains(elementH2.toString())) {
          listLinkFinal.add(elementH2.toString());
        }
      }
    }
    for (String elementH2 : listLinkFinal) {
      if (StringUtils.isNotEmpty(elementH2.toString())) {
        Matcher matcher = Pattern.compile(END_URL_PATTERN).matcher(elementH2);
        if (matcher.matches()) {
          listLink.add(matcher.group(2));
        } else {
          listLink.add("Unavailable");
        }
      }
    }
    if (bookValueList.size() == listLink.size()) {
      for (int i = 0; i < bookValueList.size(); i++) {
        bookValueList.get(i).setIsbn(listLink.get(i));
      }
    }
    return bookValueList;
  }

}
