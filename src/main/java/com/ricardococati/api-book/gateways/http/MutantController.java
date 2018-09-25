package com.ricardococati.api-book.gateways.http;

import static java.util.Objects.isNull;

import com.ricardococati.api-book.domains.DnaConverter;
import com.ricardococati.api-book.usecases.IMutantUsecase;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(
    value = "/api/v1/mutant",
    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class MutantController {

  private final IMutantUsecase mutantUsecase;

  @ApiOperation(
      value = "verifyDnaSequence",
      notes = "Verify dna sequence",
      response = Boolean.class)
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Boolean> verifyDnaSequence(@RequestBody DnaConverter dnaSequence) {
    Boolean isMutant = this.mutantUsecase.verifyDna(dnaSequence.getDna());
    if (isNull(isMutant) || !isMutant) {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    return new ResponseEntity<>(isMutant, HttpStatus.OK);
  }
}
