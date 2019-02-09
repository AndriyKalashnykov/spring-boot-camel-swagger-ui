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

# Kubernetes
https://kubernetes.io/docs/reference/kubectl/cheatsheet/#viewing-finding-resources

# Openshift Cheatsheet
https://gist.github.com/rafaeltuelho/111850b0db31106a4d12a186e1fbc53e
https://github.com/t0ffel/origin-aggregated-logging_2/blob/e8aec6a5ece1fd51d8915e25e4953d4df04c5843/deployer/scripts/upgrade.sh

This repository contains a demo of Spring Boot with Apache Camel and Swagger UI.

* Spring Boot 1.5.16.RELEASE
* Apache Camel 2.21.0
* Swagger UI 2.2.8

Latest OC command
---
    https://github.com/openshift/origin/releases/latest

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

http://localhost:8080/swagger-ui.html
http://localhost:8080/api/api-doc

http://localhost:8080/api/person
http://localhost:8080/api/hello

http://spring-boot-camel-swagger-ui-fuse7.6923.rh-us-east-1.openshiftapps.com/swagger-ui.html
http://spring-boot-camel-swagger-ui-fuse7.6923.rh-us-east-1.openshiftapps.com/api/api-doc

http://spring-boot-camel-swagger-ui-fuse7.6923.rh-us-east-1.openshiftapps.com/api/hello
http://spring-boot-camel-swagger-ui-fuse7.6923.rh-us-east-1.openshiftapps.com/api/person
http://spring-boot-camel-swagger-ui-fuse7.6923.rh-us-east-1.openshiftapps.com/api/ext


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

#####pod ip ( https://kubernetes.io/docs/reference/kubectl/jsonpath )
--

oc get pods --selector app=spring-boot-camel-swagger-ui -o=jsonpath="{range .items[*]}{.metadata.name}{'\t'}{.status.podIP}{'\n'}{end}"
oc get pods --selector name=jenkins -o=jsonpath="{range .items[*]}{.metadata.name}{'\t'}{.status.podIP}{'\n'}{end}"

oc get pod/jenkins-1-rzdbd -o template --template "{{.metadata.name}} {{.status.podIP}}{{'\n'}}"
oc get pod/spring-boot-camel-swagger-ui-4-4jdc4 -o template --template "{{.metadata.name}} {{.status.podIP}}{{'\n'}}"
oc get pod/jenkins-1-rzdbd -o json | python -c "import json, sys; data=json.loads(sys.stdin.read()); print(data['status']['podIP'])"

#####Pod start time

oc get pods -o=jsonpath="{range .items[*]}{.metadata.name}{'\t'}{.status.startTime}{'\n'}{end}"

#####List pods Sorted by Restart Count

oc get pods --sort-by=".status.containerStatuses[0].restartCount"

oc explain pod
oc --loglevel 7 get pod
oc --loglevel 9999 get pod
oc get pods -o wide

#####pods on a node

oc adm manage-node node --list-pods

#### Get DCs strategy type

oc get dc jenkins -o=go-template="{{ .spec.strategy.type }}"

fabric8
---

oc new-app fuse7/spring-boot-camel-swagger-ui~https://github.com/AndriyKalashnykov/spring-boot-camel-swagger-ui#master --context-dir=/ 

oc new-app fabric8/s2i-java:2.1~https://github.com/AndriyKalashnykov/spring-boot-camel-swagger-ui#master

service/routes
--
oc get services -o json
oc get endpoints -o json
oc explain route.spec.port

oc patch route/spring-boot-camel-swagger-ui -p '{"spec":{"port":{"targetPort":8080}}}'

#### deployconfig

oc scale dc/nodejs-ex --replicas=1

oc get dc/nodejs-ex -o jsonpath='{.spec.replicas}'
oc patch dc/nodejs-ex -p="{ \"spec\": { \"replicas\": 2}}"
oc patch dc/nodejs-ex -p="{ \"spec\": { \"strategy\": { \"type\": \"Recreate\" }}}"
oc patch dc/spring-boot-camel-swagger-ui -p="{ \"spec\": { \"strategy\": { \"type\": \"Recreate\" }}}"

oc get dc spring-boot-camel-swagger-ui -o json > dc.json

##### change profile
oc patch dc/spring-boot-camel-swagger-ui -p="[{\"op\": \"replace\", \"path\": \"/spec/template/spec/containers/0/env/2\", \"value\": {\"name\":\"JAVA_OPTIONS\",\"value\":\"-Dspring.profiles.active=qa\"}}]" --type=json

##### add container port
oc patch dc/spring-boot-camel-swagger-ui -p="[{\"op\": \"replace\", \"path\": \"/spec/template/spec/containers/0/ports/3\", \"value\": {\"name\":\"ping\",\"containerPort\":8888,\"protocol\":\"TCP\"}}]" --type=json

##### remove rolling params

oc patch dc/spring-boot-camel-swagger-ui -p='[{\"op\":\"remove\", \"path\": \"/spec/strategy/rollingParams\"}]' --type=json

oc patch dc/minimal-notebook --patch '{"spec":{"template":{"spec":{"serviceAccountName": "runasuid1000"}}}}'
oc patch dc spring-boot-camel-swagger-ui -p="{\"spec\": {\"template\": {\"spec\": {\"containers\": [{\"name\": \"jenkins\",\"resources\": {\"limits\": {\"cpu\": \"500m\",\"memory\": \"514Mi\"}}}]}}}}"
oc patch dc <name> --patch='{"spec":{"template":{"spec":{"containers":[{"name": "<container-name>", "image":"image-name:tag"}]}}}}'
oc patch dc dc-name --patch='{"spec":{"template":{"spec":{"containers[0]":{"image":"image-name:tag"}}}}}'
oc patch deployment myapp-deployment -p '{"spec":{"template":{"spec":{"containers":[{"name":"myapp","image":"172.20.34.206:5000/myapp:img:3.0"}]}}}}'

oc expose svc spring-boot-camel-swagger-ui --port=8081
WARNING] F8: No such generated manifest file C:\projects\spring-boot-camel-swagger-ui-github\target\classes\META-INF\fabric8\openshift.yml for this project so ignoring

#### Environment variables



#### Role binding 

oc create rolebinding default-view --clusterrole=view --serviceaccount=fuse7:default --namespace=fuse7

####Deploy
--
mvn clean package fabric8:deploy -Popenshift

Chage config
---
mvn package fabric8:apply

Undelploy
--
mvn fabric8:undeploy

Image streams
---

oc get is -n openshift
oc describe is -n openshift fis-java-openshift 

image stream of particular image
--
oc get is -n openshift | grep ^redhat-openjdk | cut -f1 -d " "


Templates
---

oc get template -n openshift


NodeJS example
--

oc delete all --selector app=nodejs-ex
oc delete all --selector app=nodejs-ex-pipeline

oc new-app https://github.com/sclorg/nodejs-ex --context-dir=openshift/pipeline --name nodejs-ex-pipeline
oc logs -f bc/nodejs-ex-pipeline
oc start-build nodejs-ex --follow
oc get svc
oc expose svc/nodejs-ex

####New project

mvn org.apache.maven.plugins:maven-archetype-plugin:2.4:generate -DarchetypeCatalog=https://maven.repository.redhat.com/ga/io/fabric8/archetypes/archetypes-catalog/2.2.0.fuse-720018-redhat-00001/archetypes-catalog-2.2.0.fuse-720018-redhat-00001-archetype-catalog.xml -DarchetypeGroupId=org.jboss.fuse.fis.archetypes -DarchetypeArtifactId=spring-boot-camel-xml-archetype -DarchetypeVersion=2.2.0.fuse-720018-redhat-00001
