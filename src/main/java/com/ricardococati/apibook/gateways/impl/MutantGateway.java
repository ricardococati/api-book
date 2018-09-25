package com.ricardococati.apibook.gateways.impl;

import com.ricardococati.apibook.domains.Dna;
import com.ricardococati.apibook.gateways.IMutantGateway;
import com.ricardococati.apibook.gateways.repository.MutantRepository;
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
