#! /bin/bash

# https://github.com/t0ffel/origin-aggregated-logging_2/blob/e8aec6a5ece1fd51d8915e25e4953d4df04c5843/deployer/scripts/upgrade.sh

set -ex

dcs=$(oc get dc -o jsonpath='{.items[*].metadata.name}')
  for dc in $dcs; do
#    oc annotate dc/${dc} previousReplicas=$(oc get dc/${dc} -o jsonpath='{.spec.replicas}') --overwrite
#    oc scale --replicas=0 dc $dc
    oc get dc/${dc}
done