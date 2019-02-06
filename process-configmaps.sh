#! /bin/bash

#set -ex

# skip 1st row
# sed 1d

# get 2nd row
# sed -n 2p

dcs=$(oc get configmaps --selector app=spring-boot-camel-swagger-ui | sed 1d | cut -f1 -d " ")
  for dc in $dcs; do
  echo ${dc}
done