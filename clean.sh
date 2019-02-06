#! /bin/bash

set -ex

#oc delete all --selector app=nodejs-ex
#oc delete all --selector app=nodejs-ex-pipeline

#oc delete all --selector name=spring-boot-configmaps-demo
#oc delete all --selector app=spring-boot-configmaps-demo
#oc delete all,configmap,pvc,serviceaccount,rolebinding --selector app=spring-boot-configmaps-demo

#| cut -f1 -d " "

#dcs=$(oc get configmaps --selector app=spring-boot-configmaps-demo ls -l | sed -n 2p )
#  for dc in $dcs; do
#  echo ${dc}
#  oc delete configmap ${dc}
#done

APP_NAME=spring-boot-camel-swagger-ui

oc delete all,secret,configmap,pvc,serviceaccount,rolebinding --selector app=$APP_NAME
oc delete secrets --selector app=$APP_NAME

# nuclear option
# oc delete all -l provider=fabric8 --grace-period=0 --force

# mvn fabric8:undeploy

