# spring-boot-camel-swagger-ui
# https://access.redhat.com/documentation/en-us/red_hat_fuse/7.2/html-single/release_notes/#FISDistrib

# Red Hat Fuse Supported Standards
# https://access.redhat.com/articles/375743

# Red Hat Fuse - Component Details
# https://access.redhat.com/articles/348423

This repository contains a demo of Spring Boot with Apache Camel and Swagger UI.

* Spring Boot 1.5.16.RELEASE
* Apache Camel 2.21.0
* Swagger UI 2.2.8

Docker
---
docker image rm spring-boot-camel-swagger-ui
docker build --tag spring-boot-camel-swagger-ui .
docker run --rm -p 8080:8080 -p 8081:8081 spring-boot-camel-swagger-ui

Tag
--- 
git tag -f spring-boot-camel-swagger-ui
git push -f --tags

or

git tag -d spring-boot-camel-swagger-ui
git push origin :refs/tags/spring-boot-camel-swagger-ui
git tag spring-boot-camel-swagger-ui
git push origin spring-boot-camel-swagger-ui

Run
---
java -jar target/spring-boot-swagger-0.0.1-SNAPSHOT.jar
java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n -jar target/spring-boot-swagger-0.0.1-SNAPSHOT.jar
mvn clean package spring-boot:run


curl -X GET -H "Content-type: application/json" -H "Accept: application/json"  "http://localhost:8080/api/person"

http://localhost:8080/swagger/index.html

http://localhost:8080/api-doc
http://localhost:8080/api-doc/spring-boot-swagger

http://localhost:8080/api/person
http://localhost:8080/api/hello



Health checks
---
http://localhost:8081/health
http://localhost:8081/metrics
http://localhost:8081/env
http://localhost:8081/beans


http://localhost:8081/camel/health/check
http://localhost:8081/camel/routes


OCP
---

oc get all --selector app=s2i-fuse70-spring-boot-camel-swagger-ui -o name
oc delete all --selector app=s2i-fuse70-spring-boot-camel-swagger-ui
oc delete all,configmap,pvc,serviceaccount,rolebinding --selector app=s2i-fuse70-spring-boot-camel-swagger-ui