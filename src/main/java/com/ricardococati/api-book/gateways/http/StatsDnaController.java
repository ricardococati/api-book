package com.ricardococati.api-book.gateways.http;

import static java.util.Objects.isNull;

import com.ricardococati.api-book.domains.DnaStats;
import com.ricardococati.api-book.domains.DnaStatsConverter;
import com.ricardococati.api-book.usecases.IMutantUsecase;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(
    value = "/api/v1/stats",
    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class StatsDnaController {

  private final IMutantUsecase mutantUsecase;

  @ApiOperation(
      value = "countDnaStats",
      notes = "Count DNA Stats",
      response = DnaStatsConverter.class)
  @RequestMapping(method = RequestMethod.GET)
  @PostMapping(value = "/")
  public ResponseEntity<DnaStatsConverter> countDnaStats() {
    DnaStats statsDna = this.mutantUsecase.statsDna();
    if (isNull(statsDna)) {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    DnaStatsConverter statsDnaConverter = DnaStatsConverter.dnaStatsConverter(statsDna);
    return new ResponseEntity<>(statsDnaConverter, HttpStatus.OK);
  }
}
