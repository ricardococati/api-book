package com.ricardococati.api-book.usecases.impl;

import com.ricardococati.api-book.exceptions.ValidationException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ValidateDnaSequenceTest {

  @InjectMocks
  private ValidateDnaSequence target;
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void verifyDnaArraySizeValid() throws ValidationException {
    //GIVEN
    String[] dna = {"AAAAAA", "AAAAAA", "CCCCCC", "CCCCCC", "GGGGGG", "TTTTTT"};
    //WHEN
    Boolean retorno = target.validateDnaSequence(dna);
    //THEN
    Assert.assertNotNull(retorno);
    Assert.assertTrue(retorno);
  }

  @Test
  public void verifyDnaArraySizeInvalid() throws ValidationException {
    //GIVEN
    String[] dna = {"AAAAAA", "AAAAAA", "CCCCCC", "CCCCCC"};
    //WHEN
    Boolean retorno = target.validateDnaSequence(dna);
    //THEN
    Assert.assertFalse(retorno);
  }
}