# spring-boot-camel-swagger-ui

This repository contains a demo of Spring Boot with Apache Camel and Swagger UI.

* Spring Boot 1.5.9
* Apache Camel 2.20.1
* Swagger UI 2.2.8

Docker
---
docker image rm spring-boot-camel-swagger-ui
docker build --tag spring-boot-camel-swagger-ui .
docker run --rm -p 8080:8080 -p 8081:8081 spring-boot-camel-swagger-ui

Tag
--- 
git tag spring-boot-camel-swagger-ui
git tag -f spring-boot-camel-swagger-ui
git push origin --tags

java -jar target/spring-boot-swagger-0.0.1-SNAPSHOT.jar
java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n -jar target/spring-boot-swagger-0.0.1-SNAPSHOT.jar
mvn clean package spring-boot:run


curl -X GET -H "Content-type: application/json" -H "Accept: application/json"  "http://localhost:8080/api/person"

http://localhost:8080/swagger/index.html

http://localhost:8080/api-doc
http://localhost:8080/api-doc/spring-boot-swagger

http://localhost:8080/api/person


Health checks
---
http://localhost:8081/health
http://localhost:8081/metrics
http://localhost:8081/env
http://localhost:8081/beans

http://localhost:8080/camel/health/check


OCP
---

oc get all --selector app=s2i-fuse70-spring-boot-camel -o name
oc delete all --selector app=s2i-fuse70-spring-boot-camel
oc delete all,configmap,pvc,serviceaccount,rolebinding --selector app=s2i-fuse70-spring-boot-camel