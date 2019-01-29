# spring-boot-camel-swagger-ui
https://access.redhat.com/documentation/en-us/red_hat_fuse/7.2/html-single/release_notes/#FISDistrib

# Red Hat Fuse Supported Standards
https://access.redhat.com/articles/375743

# Red Hat Fuse - Component Details
https://access.redhat.com/articles/348423

# Fuse on OpenShift Guide
https://access.redhat.com/documentation/en-us/red_hat_fuse/7.2/pdf/fuse_on_openshift_guide/Red_Hat_Fuse-7.2-Fuse_on_OpenShift_Guide-en-US.pdf

# fabric8-maven-plugin
https://github.com/fabric8io/fabric8-maven-plugin

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
app name: s2i-fuse70-spring-boot-camel-swagger-ui
git repository url: https://github.com/AndriyKalashnykov/spring-boot-camel-swagger-ui
git reference: spring-boot-camel-swagger-ui

oc get all --selector app=s2i-fuse70-spring-boot-camel-swagger-ui -o name
oc delete all --selector app=s2i-fuse70-spring-boot-camel-swagger-ui
oc delete all,configmap,pvc,serviceaccount,rolebinding --selector app=s2i-fuse70-spring-boot-camel-swagger-ui

oc get pods -w
oc get services
oc expose service s2i-fuse70-spring-boot-camel-swagger-ui
oc get routes
oc scale --replicas=2 dc s2i-fuse70-spring-boot-camel-swagger-ui

oc get template -n openshift
oc new-project test
oc project test

mvn fabric8:apply
mvn fabric8:undeploy
mvn fabric8:deploy -Popenshift