package com.ricardococati.apibook.usecases.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import com.ricardococati.apibook.domains.DnaStats;
import com.ricardococati.apibook.exceptions.ValidationException;
import com.ricardococati.apibook.gateways.impl.MutantGateway;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MutantUsecaseTest {

  @InjectMocks
  private MutantUsecase target;
  @Rule
  public ExpectedException exception = ExpectedException.none();
  @Mock
  private ValidateDnaSequence validateDnaSequence;
  @Mock
  private MutantGateway mutantGateway;

  @Test
  public void verifyDnaSequenceNotNull() throws ValidationException {
    //GIVEN
    String[] dna = null;
    //WHEN
    Boolean returned = target.verifyDna(dna);
    //THEN
    assertNotNull(returned);
    Assert.assertFalse(returned);
  }

  @Test
  public void verifyDnaArraySizeValidReturnFalse() throws ValidationException {
    //GIVEN
    String[] dna = {"AAAAAA", "AAAAAA", "CCCCCC", "CCCCCC", "GGGGGG", "TTTTTT"};
    //WHEN
    Boolean retorno = target.verifyDna(dna);
    //THEN
    assertNotNull(retorno);
    Assert.assertFalse(retorno);
  }

  @Test
  public void verifyDnaArraySizeValidReturnTrue() throws ValidationException {
    //GIVEN
    String[] dna = {"AAAAAA", "AAAAAA", "CCCCCC", "CCCCCC", "GGGGGG", "TTTTTT"};
    when(validateDnaSequence.validateDnaSequence(dna)).thenReturn(true);
    //WHEN
    Boolean retorno = target.verifyDna(dna);
    //THEN
    assertNotNull(retorno);
    Assert.assertTrue(retorno);
  }

  @Test
  public void statsDnaReturn50Percent() throws ValidationException {
    //GIVEN
    when(mutantGateway.countIsMutant(true)).thenReturn(10);
    when(mutantGateway.countIsMutant(false)).thenReturn(10);
    //WHEN
    DnaStats retorno = target.statsDna();
    //THEN
    assertNotNull(retorno);
    assertThat(retorno.getCountMutantDna()).isEqualTo(10);
    assertThat(retorno.getCountHumanDna()).isEqualTo(10);
    assertThat(retorno.getRatio()).isEqualTo(50.0);
  }

  @Test
  public void statsDnaReturn60Percent() throws ValidationException {
    //GIVEN
    when(mutantGateway.countIsMutant(true)).thenReturn(60);
    when(mutantGateway.countIsMutant(false)).thenReturn(40);
    //WHEN
    DnaStats retorno = target.statsDna();
    //THEN
    assertNotNull(retorno);
    assertThat(retorno.getCountMutantDna()).isEqualTo(60);
    assertThat(retorno.getCountHumanDna()).isEqualTo(40);
    assertThat(retorno.getRatio()).isEqualTo(60.0);
  }

}