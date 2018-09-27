package com.ricardococati.apibook.gateways.http;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ricardococati.apibook.gateways.BookGateway;
import com.ricardococati.apibook.gateways.converter.BookConverter;
import com.ricardococati.apibook.usecases.CreateBook;
import com.ricardococati.apibook.usecases.FindBook;
import com.ricardococati.apibook.usecases.FindExternalBook;
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
  @MockBean private FindExternalBook findExternalBookMock;
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
                .content(objectMapper.writeValueAsString(buildBook()))
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
    result
        .andExpect(status().isFound())
        .andExpect(jsonPath("$[0].id", is("001")))
        .andExpect(jsonPath("$[0].description", is("001")))
        .andExpect(jsonPath("$[0].title", is("001")))
        .andExpect(jsonPath("$[0].language", is("pt")))
        .andExpect(jsonPath("$[0].ISBN", is(1)));
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
    result
        .andExpect(status().isFound())
        .andExpect(jsonPath("$.id", is("001")))
        .andExpect(jsonPath("$.description", is("001")))
        .andExpect(jsonPath("$.title", is("001")))
        .andExpect(jsonPath("$.language", is("pt")))
        .andExpect(jsonPath("$.ISBN", is(1)));
  }

  @Test
  public void findBookByIdThenReturnExceptionTest() throws Exception {
    // GIVEN
    when(findBookMock.findByIdBook(anyString())).thenThrow(new RuntimeException());
    // WHEN
    final ResultActions result =
        this.mockMvc.perform(
            get("/api/v1/books/TCACTG")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // THEN
    result.andExpect(status().isNotFound());
  }

  @Test
  public void findBookByURLThenReturnOkTest() throws Exception {
    // GIVEN
    List<BookConverter> bookConverterList = Arrays.asList(buildBook());
    when(findExternalBookMock.findBookByURL()).thenReturn(bookConverterList);
    // WHEN
    final ResultActions result =
        this.mockMvc.perform(
            get("/api/v1/books/findbyurl")
                .content(objectMapper.writeValueAsString(Arrays.asList(buildBook())))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // THEN
    result
        .andExpect(status().isFound())
        .andExpect(jsonPath("$[0].id", is("001")))
        .andExpect(jsonPath("$[0].description", is("001")))
        .andExpect(jsonPath("$[0].title", is("001")))
        .andExpect(jsonPath("$[0].language", is("pt")))
        .andExpect(jsonPath("$[0].ISBN", is(1)));
  }

  @Test
  public void findBookByURLThenReturnNullTest() throws Exception {
    // GIVEN
    when(findExternalBookMock.findBookByURL()).thenReturn(null);
    // WHEN
    final ResultActions result =
        this.mockMvc.perform(
            get("/api/v1/books/findbyurl")
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
