package com.ricardococati.api-book.gateways.repository;

import com.ricardococati.api-book.domains.Dna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MutantRepository  extends JpaRepository<Dna, Integer> {

  boolean existsByDnaSequence(String[] dnaSequence);
  Integer countAllByIsMutant(Boolean isMutant);
}
