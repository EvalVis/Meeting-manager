# Meeting Manager

## Prerequisites for using the product

Have Java 17 or later installed.

## Using the API

Download the jar file from [releases](https://github.com/EvalVis/Meeting-manager/releases).

Execute: `java -jar target/meeting-manager.jar --server.port=<port, if not entered 8080 will be used>`.

## Prerequisites for developing the Meeting manager

Make sure you have Maven 3.9.9 or later installed.

If you have a lower Maven versions the Meeting manager is not guaranteed to work.

## Running the application with Maven

Run: `mvn spring-boot:run "-Dspring-boot.run.arguments=--server.port=<port, if not entered 8080 will be used>"`

## Functionality

To access the functionality documentation please run the service and visit `/swagger-ui/index.html` endpoint.

Alternatively read `MeetingController.java` and `PersonController.java` classes which have Swagger documentation.

## Running the tests with Maven

```mvn test```

[![codecov](https://codecov.io/gh/EvalVis/Meeting-manager/branch/main/graph/badge.svg)](https://codecov.io/gh/EvalVis/Meeting-manager)