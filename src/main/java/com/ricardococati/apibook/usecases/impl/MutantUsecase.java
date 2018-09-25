package com.ricardococati.apibook.usecases.impl;

import com.ricardococati.apibook.domains.Dna;
import com.ricardococati.apibook.domains.DnaStats;
import com.ricardococati.apibook.exceptions.ValidationException;
import com.ricardococati.apibook.exceptions.errors.ErrorMessages;
import com.ricardococati.apibook.gateways.IMutantGateway;
import com.ricardococati.apibook.usecases.IMutantUsecase;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MutantUsecase implements IMutantUsecase {

  private static final boolean IS_MUTANT = true;
  private static final boolean IS_HUMAN = false;
  private final ValidateDnaSequence validateDnaSequence;
  private final IMutantGateway mutantGateway;

  @Override
  public Boolean verifyDna(String[] dnaSequence) {
    Boolean returned = false;
    try {
      validateArrayNull(dnaSequence);
      returned = validateDnaSequence.validateDnaSequence(dnaSequence);
      saveDna(dnaSequence, returned);
    } catch (ValidationException e) {
      log.error(ErrorMessages.ERROR_VERIFY_DNA.getMessage(), e.getMessage());
    }
    return returned;
  }

  private void saveDna(String[] dnaSequence, Boolean returned) {
    if (!existsDna(dnaSequence)) {
      Dna dna = new Dna();
      dna.setDnaSequence(dnaSequence);
      dna.setIsMutant(returned);
      mutantGateway.save(dna);
    }
    log.info(mutantGateway.findAll().toString());
  }

  private boolean existsDna(String[] dnaSequence) {
    return mutantGateway.existsByDnaSequence(dnaSequence);
  }

  @Override
  public DnaStats statsDna() {
    DnaStats dnaStats = new DnaStats();
    dnaStats.setCountMutantDna(countIsMutant());
    dnaStats.setCountHumanDna(countIsHuman());
    dnaStats.setSumMutantAndHuman(dnaStats.getCountMutantDna() + dnaStats.getCountHumanDna());
    dnaStats.setRatio(calculatePercentageMutant(dnaStats));
    return dnaStats;
  }

  private Double calculatePercentageMutant(DnaStats dnaStats) {
    Integer countMutantDna = dnaStats.getCountMutantDna() >= 1 ? dnaStats.getCountMutantDna() : 0;
    Double percent = 0.0;
    if(countMutantDna > 0){
      percent = (((double) countMutantDna / dnaStats.getSumMutantAndHuman()) * 100);
      percent = Double.valueOf(Math.round(percent));
    }
    return percent;
  }

  private Integer countIsMutant() {
    return Optional.ofNullable(mutantGateway.countIsMutant(IS_MUTANT)).orElse(1);
  }

  private Integer countIsHuman() {
    return Optional.ofNullable(mutantGateway.countIsMutant(IS_HUMAN)).orElse(1);
  }

  private void validateArrayNull(String[] dnaSequence) throws ValidationException {
    Optional.ofNullable(dnaSequence)
        .orElseThrow(
            () -> new ValidationException(ErrorMessages.ERROR_SEQUENCE_NOT_NULL.getMessage()));
  }
}
