package com.ricardococati.apibook.usecases.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.ricardococati.apibook.domains.Book;
import com.ricardococati.apibook.gateways.BookGateway;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CreateBookImplTest {

  @InjectMocks
  private CreateBookImpl target;
  @Mock
  private BookGateway gateway;

  @Test
  public void createBookReturnTrueTest() {
    //GIVEN
    when(gateway.existsBookByIsbn(anyString())).thenReturn(false);
    when(gateway.save(any())).thenReturn(buildBook());
    //WHEN
    Boolean returned = target.createBook(buildBook());
    //THEN
    Assertions.assertThat(returned).isNotNull();
    Assertions.assertThat(returned).isTrue();
  }

  @Test
  public void createBookReturnFalseTest() {
    //GIVEN
    when(gateway.existsBookByIsbn(anyString())).thenReturn(false);
    when(gateway.save(any())).thenReturn(null);
    //WHEN
    Boolean returned = target.createBook(buildBook());
    //THEN
    Assertions.assertThat(returned).isNotNull();
    Assertions.assertThat(returned).isFalse();
  }

  @Test
  public void createBookReturnFalseExistBookTrueTest() {
    //GIVEN
    when(gateway.existsBookByIsbn(anyString())).thenReturn(true);
    //WHEN
    Boolean returned = target.createBook(buildBook());
    //THEN
    Assertions.assertThat(returned).isNotNull();
    Assertions.assertThat(returned).isFalse();
  }

  private Book buildBook() {
    return Book.builder()
        .description("001")
        .title("001")
        .language("pt")
        .isbn("1")
        .idBook("001")
        .build();
  }

}