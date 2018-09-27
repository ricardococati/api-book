package com.ricardococati.apibook.gateways.http;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ricardococati.apibook.gateways.BookGateway;
import com.ricardococati.apibook.gateways.converter.BookConverter;
import com.ricardococati.apibook.usecases.CreateBook;
import com.ricardococati.apibook.usecases.FindBook;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {BookController.class})
public class BookControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private CreateBook usecaseMock;
  @MockBean private FindBook findBookMock;
  @Autowired private ObjectMapper objectMapper;
  @MockBean private BookGateway gatewayMock;

  @Test
  public void saveBookOKTest() throws Exception {
    // GIVEN
    when(usecaseMock.createBook(any())).thenReturn(true);
    // WHEN
    final ResultActions result =
        this.mockMvc.perform(
            post("/api/v1/books/")
                .param("description", "ATGCGA, CAGTGC, TTATGT, AGAAGG, CCCCTA, TCACTG")
                .param("title", "ATGCGA, CAGTGC, TTATGT, AGAAGG, CCCCTA, TCACTG")
                .param("language", "ATGCGA, CAGTGC, TTATGT, AGAAGG, CCCCTA, TCACTG")
                .param("isbn", "12345678")
                .content(objectMapper.writeValueAsString(buildBook()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // THEN
    result.andExpect(status().isCreated());
  }

  @Test
  public void saveBookThenReturnFalseTest() throws Exception {
    // GIVEN
    when(usecaseMock.createBook(any())).thenReturn(false);
    // WHEN
    final ResultActions result =
        this.mockMvc.perform(
            post("/api/v1/books/")
                .param("description", "ATGCGA, CAGTGC, TTATGT, AGAAGG, CCCCTA, TCACTG")
                .param("title", "ATGCGA, CAGTGC, TTATGT, AGAAGG, CCCCTA, TCACTG")
                .param("language", "ATGCGA, CAGTGC, TTATGT, AGAAGG, CCCCTA, TCACTG")
                .param("isbn", "12345678")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // THEN
    result.andExpect(status().is4xxClientError());
  }

  @Test
  public void saveBookThenReturnNullTest() throws Exception {
    // GIVEN
    when(usecaseMock.createBook(any())).thenReturn(null);
    // WHEN
    final ResultActions result =
        this.mockMvc.perform(
            post("/api/v1/books/")
                .param("description", "ATGCGA, CAGTGC, TTATGT, AGAAGG, CCCCTA, TCACTG")
                .param("title", "ATGCGA, CAGTGC, TTATGT, AGAAGG, CCCCTA, TCACTG")
                .param("language", "ATGCGA, CAGTGC, TTATGT, AGAAGG, CCCCTA, TCACTG")
                .param("isbn", "12345678")
                .content(objectMapper.writeValueAsString(buildBook()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // THEN
    result.andExpect(status().is5xxServerError());
  }

  @Test
  public void findAllBookThenReturnOkTest() throws Exception {
    // GIVEN
    List<BookConverter> bookConverterList = Arrays.asList(buildBook());
    when(findBookMock.findAll()).thenReturn(bookConverterList);
    // WHEN
    final ResultActions result =
        this.mockMvc.perform(
            get("/api/v1/books/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // THEN
    result.andExpect(status().isFound());
  }

  @Test
  public void findAllBookThenReturnNullTest() throws Exception {
    // GIVEN
    when(findBookMock.findAll()).thenReturn(null);
    // WHEN
    final ResultActions result =
        this.mockMvc.perform(
            get("/api/v1/books/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // THEN
    result.andExpect(status().isNotFound());
  }

  @Test
  public void findBookByIdThenReturnOkTest() throws Exception {
    // GIVEN
    when(findBookMock.findByIdBook(anyString())).thenReturn(buildBook());
    // WHEN
    final ResultActions result =
        this.mockMvc.perform(
            get("/api/v1/books/TCACTG")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // THEN
    result.andExpect(status().isFound());
  }

  @Test
  public void findBookByIdThenReturnNullTest() throws Exception {
    // GIVEN
    when(findBookMock.findByIdBook(anyString())).thenReturn(null);
    // WHEN
    final ResultActions result =
        this.mockMvc.perform(
            get("/api/v1/books/TCACTG")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // THEN
    result.andExpect(status().isNotFound());
  }

  private BookConverter buildBook() {
    return BookConverter.builder()
        .description("001")
        .title("001")
        .language("pt")
        .isbn(1L)
        .idBook("001")
        .build();
  }
}
