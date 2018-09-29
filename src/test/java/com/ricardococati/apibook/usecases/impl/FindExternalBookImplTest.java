package com.ricardococati.apibook.usecases.impl;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.ricardococati.apibook.gateways.BookGateway;
import com.ricardococati.apibook.gateways.converter.BookConverter;
import java.io.IOException;
import java.util.List;
import org.jsoup.nodes.Document;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FindExternalBookImplTest {

  @InjectMocks
  private FindExternalBookImpl target;
  @Mock
  private BookGateway gateway;
  @Mock
  private Document document;
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void findBookByURL() throws IOException {
    //GIVEN
    when(gateway.findByURL(anyString())).thenReturn(null);
    //WHEN
    exception.expect(RuntimeException.class);
    List<BookConverter> returned = target.findBookByURL();
  }
}