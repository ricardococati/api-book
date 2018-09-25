package com.ricardococati.apibook.gateways.http;


import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ricardococati.apibook.domains.DnaStats;
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
@WebMvcTest(controllers = {StatsDnaController.class})
public class StatsDnaControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private IMutantUsecase usecaseMock;

  @MockBean
  private IMutantGateway gatewayMock;

  @Test
  public void countDnaStatsResponseOK() throws Exception {
    //GIVEN
    when(usecaseMock.statsDna()).thenReturn(buildStatsDna());
    //WHEN
    final ResultActions result = this.mockMvc
        .perform(
            get("/api/v1/stats/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    //THEN
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.count_mutant_dna", is(10)))
        .andExpect(jsonPath("$.count_human_dna", is(10)))
        .andExpect(jsonPath("$.ratio", is(2.0)));
  }

  @Test
  public void countDnaStatsResponseNull403Forbidden() throws Exception {
    //GIVEN
    when(usecaseMock.statsDna()).thenReturn(null);
    //WHEN
    final ResultActions result = this.mockMvc
        .perform(
            get("/api/v1/stats/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    //THEN
    result.andExpect(status().is4xxClientError());
  }

  private DnaStats buildStatsDna(){
    return DnaStats
        .builder()
        .countHumanDna(10)
        .countMutantDna(10)
        .ratio(2.0)
        .build();
  }

}