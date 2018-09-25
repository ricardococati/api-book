package com.ricardococati.apibook.domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DnaStatsConverter {

  @JsonProperty("count_mutant_dna")
  private Integer countMutantDna;
  @JsonProperty("count_human_dna")
  private Integer countHumanDna;
  @JsonProperty("ratio")
  private Double ratio;

  public static DnaStatsConverter dnaStatsConverter(DnaStats dnaStats){
    return DnaStatsConverter.builder()
        .countHumanDna(dnaStats.getCountHumanDna())
        .countMutantDna(dnaStats.getCountMutantDna())
        .ratio(dnaStats.getRatio())
        .build();
  }

}
