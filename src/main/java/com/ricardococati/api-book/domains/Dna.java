package com.ricardococati.api-book.domains;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Dna implements Serializable {

  private static final long serialVersionUID = -6660019736137472637L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_dna")
  private Integer idDna;

  @Column(name = "dna_sequence")
  private String[] dnaSequence;

  @Column(name = "is_mutant")
  private Boolean isMutant;

}
