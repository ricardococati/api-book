package com.ricardococati.apibook.gateways;

import com.ricardococati.apibook.domains.Dna;
import java.util.List;

public interface IMutantGateway {

  void save(Dna dna);

  List<Dna> findAll();

  boolean existsByDnaSequence(String[] dnaSequence);

  Integer countIsMutant(Boolean isMutant);
}
