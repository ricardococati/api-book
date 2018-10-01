## API Book

#### Summary
This project aims to include and consult books on database.

#### Business rules:
* Include books on database.
* List books by id.
* List all books.
* Read books from another page.


#### Technology

* [Java 1.8](http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html)
* [Spring Boot](https://projects.spring.io/spring-boot/) * Default tomcat embedded
* [MongoDB](https://www.mongodb.com/) * MongoDB in memory, used to store data
* [JUnit](https://junit.org/junit5/)
* [Jacoco](https://www.jacoco.org/)
* [Swagger](https://swagger.io/)
* [Lombok](https://projectlombok.org/)
* [Maven](https://maven.apache.org/)
* [Jsoup](https://jsoup.org/) * Used to read remote HTML
* [Google Java Style Guides Formatter](https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml)

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
Execution port default 8666 

#### URL Swagger
Connection URL:

* [Swagger URL](http://localhost:8666/swagger-ui.html)

#### Test data

```json
{
  "ISBN": 12345678,
  "id": "234KJHGFSDKJG",
  "description": "test desc",
  "language": "pt_BR",
  "title": "tests"
}
```
