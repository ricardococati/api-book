package com.ricardococati.api-book.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DnaStats {

  private Integer countMutantDna;
  private Integer countHumanDna;
  private Integer sumMutantAndHuman;
  private Double ratio;

}
