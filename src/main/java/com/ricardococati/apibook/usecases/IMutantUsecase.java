package com.ricardococati.apibook.usecases;

import com.ricardococati.apibook.domains.DnaStats;

public interface IMutantUsecase {

  Boolean verifyDna(String[] dna);

  DnaStats statsDna();
}
