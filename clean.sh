#! /bin/bash

oc delete all --selector app=nodejs-ex
oc delete all --selector app=nodejs-ex-pipeline

mvn fabric8:undeploy

oc get all --selector app=spring-boot-camel-swagger-ui -o name
oc delete all -l provider=fabric8 --grace-period=0 --force
