package com.ricardococati.api-book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.ricardococati")
public class CharlesXavierApplication {

  public static void main(String[] args) {
    SpringApplication.run(CharlesXavierApplication.class, args);
  }
}
