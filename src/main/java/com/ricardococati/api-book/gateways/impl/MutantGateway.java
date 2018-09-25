package com.ricardococati.api-book.gateways.impl;

import com.ricardococati.api-book.domains.Dna;
import com.ricardococati.api-book.gateways.IMutantGateway;
import com.ricardococati.api-book.gateways.repository.MutantRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MutantGateway implements IMutantGateway {

  private final MutantRepository mutantRepository;

  @Override
  public void save(Dna dna) {
    mutantRepository.save(dna);
  }

  @Override
  public List<Dna> findAll() {
    return mutantRepository.findAll();
  }

  @Override
  public boolean existsByDnaSequence(String[] dnaSequence) {
    return mutantRepository.existsByDnaSequence(dnaSequence);
  }

  @Override
  public Integer countIsMutant(Boolean isMutant) {
    return mutantRepository.countAllByIsMutant(isMutant);
  }
}
