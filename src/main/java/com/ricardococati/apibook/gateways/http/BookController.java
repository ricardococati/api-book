package com.ricardococati.apibook.gateways.http;

import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

import com.ricardococati.apibook.gateways.converter.BookConverter;
import com.ricardococati.apibook.usecases.CreateBook;
import com.ricardococati.apibook.usecases.FindBook;
import com.ricardococati.apibook.usecases.FindExternalBook;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(
    value = "/api/v1/books",
    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class BookController {

  private final CreateBook createBook;
  private final FindBook findBook;
  private final FindExternalBook findExternalBook;

  @ApiOperation(value = "Create a new Book", response = Boolean.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 201, message = "Created", response = Boolean.class),
        @ApiResponse(code = 409, message = "Conflict", response = Boolean.class),
        @ApiResponse(code = 500, message = "Internal server error")
      })
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Boolean> createBook(@RequestBody BookConverter book) {
    Boolean isBookSaved = this.createBook.createBook(book.bookConverter(book));
    if (isNull(isBookSaved)) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    } else if (!isBookSaved) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    return new ResponseEntity<>(isBookSaved, HttpStatus.CREATED);
  }

  @ApiOperation(value = "List all books", response = List.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 302, message = "Found", response = List.class),
        @ApiResponse(code = 404, message = "Not found")
      })
  @RequestMapping(
      method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<List<BookConverter>> listBooks() {
    List<BookConverter> lista = findBook.findAll();
    if (isEmpty(lista)) {
      return new ResponseEntity<>(lista, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<List<BookConverter>>(lista, HttpStatus.FOUND);
  }

  @ApiOperation(value = "Find book by Id", response = BookConverter.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 302, message = "Found", response = BookConverter.class),
        @ApiResponse(code = 404, message = "Not found")
      })
  @RequestMapping(
      path = "/{id}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<BookConverter> findById(@PathVariable("id") String id) {
    try {
      BookConverter bookConverter = findBook.findByIdBook(id);
      return new ResponseEntity<>(bookConverter, HttpStatus.FOUND);
    } catch (Exception ex) {
      log.debug("Error on find by Id: ", ex.getMessage());
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "List books by URL", response = List.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 302, message = "Found", response = List.class),
        @ApiResponse(code = 404, message = "Not found")
      })
  @RequestMapping(
      value = "/findbyurl",
      method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<List<BookConverter>> findBooksByURL() {
    List<BookConverter> lista = null;
    try {
      lista = findExternalBook.findBookByURL();
      if (isEmpty(lista)) {
        return new ResponseEntity<>(lista, HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<List<BookConverter>>(lista, HttpStatus.FOUND);
    } catch (IOException e) {
      log.debug("Error on find by URL: ", e.getMessage());
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
