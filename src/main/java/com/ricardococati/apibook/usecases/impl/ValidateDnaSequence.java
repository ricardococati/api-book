package com.ricardococati.apibook.usecases.impl;

import com.ricardococati.apibook.exceptions.ValidationException;
import com.ricardococati.apibook.exceptions.errors.ErrorMessages;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ValidateDnaSequence {

  private static final int SIZE_ARRAY = 6;
  private static final String REGEXP_VALID_SIZE = "([ACTG])\\1\\1\\1";
  private static int MIN_SEQUENCE_TO_MUTANT = 2;

  public Boolean validateDnaSequence(String[] dnaSequence) {
    try {
      return isMutant(generateListByDnaSequence(dnaSequence));
    } catch (ValidationException e) {
      log.error(ErrorMessages.ERROR_ON_VALIDATE_DNA_SEQUENCE.getMessage(), e.getMessage());
    }
    return false;
  }

  private List<String> generateListByDnaSequence(String[] dnaSequence) throws ValidationException {
    if (dnaSequence.length < SIZE_ARRAY || dnaSequence.length > SIZE_ARRAY) {
      throw new ValidationException(ErrorMessages.ERROR_DNA_RANGE_INVALID.getMessage());
    }
    List<String> list = new ArrayList<>();
    list.addAll(generateListDnaSequenceExcludeNull(dnaSequence));
    if (!list.isEmpty()) {
      list.addAll(rotateHorizontalArrayToVerticalList(dnaSequence));
      list.addAll(rotateHorizontalArrayToDiagonalList(dnaSequence));
    }
    return list;
  }

  private List<String> generateListDnaSequenceExcludeNull(String[] dnaSequence) {
    List<String> listDnaSequence = Arrays.asList(dnaSequence);
    List<String> listReturn = new ArrayList<>();
    listDnaSequence
        .stream()
        .filter(Objects::nonNull)
        .forEach(
            dnaSeq -> {
              listReturn.add(dnaSeq);
            });
    return listReturn;
  }

  private boolean isMutant(List<String> list) {
    AtomicBoolean returned = new AtomicBoolean(false);
    Pattern pattern = Pattern.compile(REGEXP_VALID_SIZE);
    AtomicInteger countFor = new AtomicInteger(0);
    list.forEach(
        dna -> {
          int countWhile = 0;
          Matcher matchToCountDnaMoreThen2Times = pattern.matcher(dna);
          while (matchToCountDnaMoreThen2Times.find(countWhile)) {
            countFor.getAndIncrement();
            if (countFor.get() >= MIN_SEQUENCE_TO_MUTANT) {
              returned.set(true);
              log.info("Sequence DNA is find: " + dna);
            }
            countWhile = matchToCountDnaMoreThen2Times.end();
          }
        });

    return returned.get();
  }

  public List<String> rotateHorizontalArrayToVerticalList(String[] dnaSequence) {
    char[][] dnaMatrix = strArrayToMatrix(dnaSequence);
    dnaMatrix = rotateMatrix(dnaMatrix);
    return matrixToList(dnaMatrix);
  }

  public List<String> rotateHorizontalArrayToDiagonalList(String[] dnaSequence) {
    char[][] dnaMatrix = strArrayToMatrix(dnaSequence);
    List<String> listDiagonal = new ArrayList<>();

    listDiagonal.addAll(getDiagnolToRight(dnaMatrix));
    dnaMatrix = rotateMatrix(dnaMatrix);
    listDiagonal.addAll(getDiagnolToRight(dnaMatrix));

    return listDiagonal;
  }

  private List<String> getDiagnolToRight(char[][] dnaMatrix) {
    int length = dnaMatrix.length;
    List<String> listReturn = new ArrayList<>();
    StringBuilder rowMovingSb;
    StringBuilder colMovingSb;
    for (int pos = 0; pos < length; pos++) {
      rowMovingSb = new StringBuilder();
      colMovingSb = new StringBuilder();
      for (int dif = 0; pos + dif < length; dif++) {
        rowMovingSb.append(dnaMatrix[dif][pos + dif]);
        colMovingSb.append(dnaMatrix[pos + dif][dif]);
      }
      listReturn.add(rowMovingSb.toString());
      listReturn.add(colMovingSb.toString());
    }
    listReturn.remove(0);
    return listReturn;
  }

  private char[][] rotateMatrix(char[][] dnaMatrix) {
    int arraySize = dnaMatrix.length;
    char[][] returned = new char[arraySize][arraySize];
    int newCol;
    for (int row = 0; row < arraySize; row++) {
      for (int col = 0; col < arraySize; col++) {
        newCol = (-1) * (col - arraySize + 1);
        returned[newCol][row] = dnaMatrix[row][col];
      }
    }
    return returned;
  }

  private char[][] strArrayToMatrix(String[] dnaSequence) {
    int length = dnaSequence.length;
    char[][] dnaMatrix = new char[length][length];
    for (int row = 0; row < length; row++) {
      dnaMatrix[row] = dnaSequence[row].toCharArray();
    }
    return dnaMatrix;
  }

  private List<String> matrixToList(char[][] dnaMatrix) {
    List<String> strList = new ArrayList<>();
    for (char[] inputRow : dnaMatrix) {
      strList.add(new String(inputRow));
    }
    return strList;
  }
}
