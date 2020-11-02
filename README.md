# Ubitricity carpark service

This service is provides a simple application to manage a carpark ubi
The project is consist of two maven modules
* frontend
* backend

## Backend

This is a simple spring boot application. For persistence we use H2 and spring jpa.
API is available via swagger-ui `http://localhost:8081/swagger-ui.html`
 

## Frontend 

This is a Vue.js application. For sake of simplicity we use a vue-bootstrap

## How to run

To run application please use following command

```shell script
mvn package
mvn --projects backend spring-boot:run
```

## Run via docker
