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

# FUSE application templates
https://github.com/jboss-fuse/application-templates
https://github.com/jboss-fuse/application-templates/blob/master/quickstarts/karaf-cxf-rest-template.json 

# FUSE Karaf image
https://access.redhat.com/containers/?tab=overview#/registry.access.redhat.com/fuse7/fuse-karaf-openshift

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
http://localhost:8080/health
http://localhost:8080/metrics
http://localhost:8080/env
http://localhost:8080/beans


http://localhost:8080/camel/health/check
http://localhost:8080/camel/routes


OCP
---
app name: spring-boot-camel-swagger-ui
git repository url: https://github.com/AndriyKalashnykov/spring-boot-camel-swagger-ui
git reference: spring-boot-camel-swagger-ui

oc get all --selector app=spring-boot-camel-swagger-ui -o name
oc delete all --selector app=spring-boot-camel-swagger-ui
oc delete all,configmap,pvc,serviceaccount,rolebinding --selector app=spring-boot-camel-swagger-ui
oc delete all,configmap,pvc,serviceaccount,rolebinding --selector app=s2i-fuse72-karaf-cxf-rest

cleanup all created by fabric8
--
oc get all --selector app=spring-boot-camel-swagger-ui -o name
oc delete all -l provider=fabric8 --grace-period=0 --force

oc delete all,configmap,pvc,serviceaccount,rolebinding --selector name=jenkins
oc delete all,configmap,pvc,serviceaccount,rolebinding --selector app=jenkins-persistent
oc get all


Jenkins
---

oc new-app jenkins-persistent

oc get all --selector app=jenkins-persistent
oc get all --selector name=jenkins
--- won't work, why?
oc get all --selector name=jenkins,app=jenkins-persistent

oc get all -l name=jenkins -l app=jenkins-persistent


oc get services
oc get svc
oc expose service s2i-fuse70-spring-boot-camel-swagger-ui
oc get routes
oc scale --replicas=2 dc s2i-fuse70-spring-boot-camel-swagger-ui

oc logs -f <pod>

oc get template -n openshift
oc new-project test
oc project test

images
--

oc import-image java:8 --from=registry.access.redhat.com/redhat-openjdk-18/openjdk18-openshift --confirm
oc import-image fuse7/fuse-karaf-openshift:latest --from=registry.access.redhat.com/fuse7/fuse-karaf-openshift --confirm

oc delete istag/fuse-karaf-openshift:latest
oc delete istag/java:8

pods
---

execute remot command
--
oc exec <pod> date

log in
--
oc rsh <pod>

get pods for app
--
oc get pods --selector app=s2i-fuse72-karaf-cxf-rest

force delete node
-- 
oc delete po/spring-boot-camel-swagger-ui-s2i-1-build --grace-period=0 --force=true --ignore-not-found=true

oc explain pod
oc --loglevel 7 get pod
oc --loglevel 9999 get pod
oc get pods -o wide

pods on a node
--
oc adm manage-node node --list-pods

fabric8
---

oc new-app fabric8/s2i-java:2.1~https://github.com/AndriyKalashnykov/spring-boot-camel-swagger-ui#master

Create service/routes
--
oc get services -o json
oc get endpoints -o json
oc explain route.spec.port

oc patch route/spring-boot-camel-swagger-ui -p '{"spec":{"port":{"targetPort":8080}}}'

oc expose svc spring-boot-camel-swagger-ui --port=8081
WARNING] F8: No such generated manifest file C:\projects\spring-boot-camel-swagger-ui-github\target\classes\META-INF\fabric8\openshift.yml for this project so ignoring

Deploy
--
mvn clean package fabric8:deploy
mvn clean package fabric8:resource fabric8:deploy -Popenshift

Chage config
---
mvn package fabric8:apply

Undelploy
--
mvn fabric8:undeploy

Image streams
---

oc get is -n openshift

image stream of particular image
--
oc get is -n openshift | grep ^redhat-openjdk | cut -f1 -d " "



NodeJS example
--

oc delete all --selector app=nodejs-ex
oc delete all --selector app=nodejs-ex-pipeline

oc new-app https://github.com/sclorg/nodejs-ex --context-dir=openshift/pipeline --name nodejs-ex-pipeline
oc logs -f bc/nodejs-ex-pipeline
oc start-build nodejs-ex --follow
oc get svc
oc expose svc/nodejs-ex

oc delete all --selector app=nodejs-ex