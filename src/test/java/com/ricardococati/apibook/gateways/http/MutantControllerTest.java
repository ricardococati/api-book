package com.ricardococati.apibook.gateways.http;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ricardococati.apibook.domains.DnaConverter;
import com.ricardococati.apibook.gateways.IMutantGateway;
import com.ricardococati.apibook.usecases.IMutantUsecase;
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
@WebMvcTest(controllers = {MutantController.class})
public class MutantControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private IMutantUsecase usecaseMock;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private IMutantGateway gatewayMock;

  @Test
  public void verifyDnaSequenceResponseOK() throws Exception {
    // GIVEN
    when(usecaseMock.verifyDna(any(String[].class))).thenReturn(true);
    // WHEN
    final ResultActions result =
        this.mockMvc.perform(
            post("/api/v1/mutant/")
                .content(objectMapper.writeValueAsString(buildDna()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // THEN
    result.andExpect(status().isOk());
  }

  @Test
  public void verifyDnaSequenceResponseFalse403Forbiden() throws Exception {
    // GIVEN
    when(usecaseMock.verifyDna(any(String[].class))).thenReturn(false);
    // WHEN
    final ResultActions result =
        this.mockMvc.perform(
            post("/api/v1/mutant/")
                .param("dna", "ATGCGA, CAGTGC, TTATGT, AGAAGG, CCCCTA, TCACTG")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // THEN
    result.andExpect(status().is4xxClientError());
  }

  @Test
  public void verifyDnaSequenceResponseNull403Forbiden() throws Exception {
    // GIVEN
    when(usecaseMock.verifyDna(any(String[].class))).thenReturn(null);
    // WHEN
    final ResultActions result =
        this.mockMvc.perform(
            post("/api/v1/mutant/")
                .param("dna", "ATGCGA, CAGTGC, TTATGT, AGAAGG, CCCCTA, TCACTG")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // THEN
    result.andExpect(status().is4xxClientError());
  }

  private DnaConverter buildDna() {
    return DnaConverter.builder()
        .dna(new String[] {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"})
        .build();
  }
}
