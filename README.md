# spring-boot-camel-swagger-ui

This repository contains a demo of Spring Boot with Apache Camel and Swagger UI.

* Spring Boot 1.5.9
* Apache Camel 2.20.1
* Swagger UI 2.2.8

docker image rm spring-boot-camel-swagger-ui
docker build --tag spring-boot-camel-swagger-ui .
docker run --rm -p 8080:8080 -p 8081:8081 spring-boot-camel-swagger-ui

curl -X GET -H "Content-type: application/json" -H "Accept: application/json"  "http://localhost:8080/api/person"

http://localhost:8080/swagger/index.html

http://localhost:8080/api-doc
http://localhost:8080/api-doc/spring-boot-swagger

http://localhost:8080/api/person


Health checks
---
http://localhost:8080/health
http://localhost:8080/camel/health/check
