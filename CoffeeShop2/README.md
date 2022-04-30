
# INTRODUCTION


This service implements the exercise asked by Basement Crowd, following the instructions given in https://bitbucket.org/basementcrowd/backend-engineer-api/src/master/

The service has been built with Spring Boot 2, a very well-known framework based in Java, that it's able to develop in a fast and easy way a web service, creating standalone applications, and no need for the deployment of war files.

cambio 1

cambio 2

cambio 3

## TECHNICAL NOTES

* It's a Spring application, built with Java 8 and packaged using Maven
* The service is divided in two main resources : coffees and purchases
* The selected backend database is H2, a fast, open source memory database that fits in this scenario
* It is not possible to delete a coffee that it's using in a purchase, to guarantee the referential integrity
* You can update the name and the price of a coffee but you can't modify the id, due that it's the primary key


## DEPENDENCIES

The program depends on the main Spring Boot libraries and the following Java Libraries for running: lombok, openapi.

For testing we use the junit and assertj framework.

## PREREQUISITES

It's necessary to have an installation of Java8 and maven in the machine where it goes to deploy

## RUNNING

The service works out of the box with maven and Java8, running the following command inside the directory CoffeeShop (default port 8080) :

mvn spring-boot:run


## TESTING

For running all the tests of the service, use this command :

mvn clean test

Also, to get the code coverage of these tests, run this command :

mvn clean verify

And the results are in the next path :  target/site/jacoco/index.html


## DOCUMENTATION

The best way to know how to use the API is looking at the Open API specification inside the Swagger UI, that it founds in the following URL :
	http://localhost:8080/swagger-ui.html

