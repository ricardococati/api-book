## Charles Xavier

#### Summary
This project aims to validate the DNA sequence of a mutant. For this the system receives the DNA sequence, and through a simple method of validation of the sequence, attests if the DNA is of a human or mutant. It also exposes an api that informs the percentage of mutants that were found in DNA sequence analyzes.

#### Business rules:
* Validation of the DNA sequence.
* Saves all searches performed on the database.
* Method of calculating the percentage of mutants found.


#### Technology

* [Java 1.8](http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html)
* [Spring Boot](https://projects.spring.io/spring-boot/) * Tomcat embedded
* [HSQLDB](http://hsqldb.org/) * Used to store data from each DNA sequence check
* [JUnit](https://junit.org/junit5/)
* [Jacoco](https://www.jacoco.org/)
* [Swagger](https://swagger.io/)
* [Lombok](https://projectlombok.org/)
* [Maven](https://maven.apache.org/)

#### Prerequisites for local execution:
Have Java installed on the local machine;

Have maven installed, starting with version 3.3;

#### Steps for project execution

On the root folder of the project run the command: 
```
mvn clean install
```
After performing the application build run the application with the following command:

```
mvn spring-boot:run
```

#### Execution port
Execution port default 8082 

#### URL Swagger
Connection URL:

* [Swagger URL](http://localhost:8082/swagger-ui.html)

#### Test data

```json
{
  "dna": [
    "ATGCGA",
    "CAGTGC",
    "TTATGT",
    "AGAAGG",
    "CCCCTA",
    "TCACTG"
  ]
}
```
### Endpoint published on Azure:
URL publish endpoint on Microsoft Azure on port 8080:
http://52.238.208.54:8080/api/v1/mutant/
Test data:
```json
{
  "dna": [
    "ATGCGA",
    "CAGTGC",
    "TTATGT",
    "AGAAGG",
    "CCCCTA",
    "TCACTG"
  ]
}
```
Endpoint stats:
http://52.238.208.54:8080/api/v1/stats/
