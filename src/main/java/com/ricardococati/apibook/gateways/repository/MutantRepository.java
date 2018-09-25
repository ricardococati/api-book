package com.ricardococati.apibook.gateways.repository;

import com.ricardococati.apibook.domains.Dna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MutantRepository  extends JpaRepository<Dna, Integer> {

  boolean existsByDnaSequence(String[] dnaSequence);
  Integer countAllByIsMutant(Boolean isMutant);
}
