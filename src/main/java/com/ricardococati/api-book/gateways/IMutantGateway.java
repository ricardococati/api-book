package com.ricardococati.api-book.gateways;

import com.ricardococati.api-book.domains.Dna;
import java.util.List;

public interface IMutantGateway {

  void save(Dna dna);

  List<Dna> findAll();

  boolean existsByDnaSequence(String[] dnaSequence);

  Integer countIsMutant(Boolean isMutant);
}
