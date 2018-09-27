package com.ricardococati.apibook.usecases;

import com.ricardococati.apibook.exceptions.ValidationException;
import com.ricardococati.apibook.gateways.converter.BookConverter;
import java.util.List;

public interface FindBook {

  BookConverter findByIdBook(String idBook) throws ValidationException;

  List<BookConverter> findAll();
}
