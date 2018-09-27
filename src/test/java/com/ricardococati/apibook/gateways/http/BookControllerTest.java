package com.ricardococati.apibook.gateways.http;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ricardococati.apibook.gateways.BookGateway;
import com.ricardococati.apibook.gateways.converter.BookConverter;
import com.ricardococati.apibook.usecases.CreateBook;
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
