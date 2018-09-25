package com.ricardococati.api-book.usecases;

import com.ricardococati.api-book.domains.DnaStats;

public interface IMutantUsecase {

  Boolean verifyDna(String[] dna);

  DnaStats statsDna();
}
