#!/usr/bin/env groovy

import hudson.model.*

// https://jenkins.io/doc/book/pipeline/jenkinsfile/
// https://jenkins.io/blog/2017/02/07/declarative-maven-project/
// https://github.com/openshift/jenkins-plugin
// https://github.com/openshift/jenkins-client-plugin
// https://wilsonmar.github.io/jenkins2-pipeline/

library identifier: "pipeline-library@master",
        retriever: modernSCM(
                [
                        $class: "GitSCMSource",
                        remote: "https://github.com/redhat-cop/pipeline-library.git"
                ]
        )

@Library('github.com/fabric8io/fabric8-pipeline-library@master')
def setupScript = null
def PREVIOUS_BUILD_NUMBER = null

node('maven') {

//    agent {
//        label 'maven'
//    }

    stage('Init') {
        echo 'Init'
        sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                    echo "JAVA_HOME = ${JAVA_HOME}"                   
                '''
        sh "java -version"
        sh "mvn -version"

        sh "printenv"

        echo "\u2600 BUILD_URL=${env.BUILD_URL}"
        echo "\u2600 JENKINS_URL=${env.JENKINS_URL}"
        echo "\u2600 GIT_URL=${env.GIT_URL}"
        echo "\u2600 JOB_NAME=${env.JOB_NAME}"
        def workspace = pwd()
        echo "\u2600 workspace=${workspace}"

        tokens = "${env.JOB_NAME}".tokenize('/')
        org = tokens[0]
        repo = tokens[1]
        branch = tokens[2]
        echo 'account-org/repo/branch=' + org +'/'+ repo +'/'+ branch

        def scmUrl = scm.getUserRemoteConfigs()[0].getUrl()
        echo 'scmUrl = ${scmUrl}'
        echo scm.getUserRemoteConfigs()

//        env.PREVIOUS_BUILD_NUMBER = sh returnStdout: true, script: '''curl -sk ${BUILD_URL}/lastStableBuild/buildNumber'''
//        PREVIOUS_BUILD_NUMBER = env.PREVIOUS_BUILD_NUMBER
//        sh 'echo PREVIOUS_BUILD_NUMBER 1  = ${env.PREVIOUS_BUILD_NUMBER}'
//        sh 'echo PREVIOUS_BUILD_NUMBER 2 = ${PREVIOUS_BUILD_NUMBER}'
//        echo 'PREVIOUS_BUILD_NUMBER 3 = ${PREVIOUS_BUILD_NUMBER}'
    }

    stage('Checkout SCM') {
        echo 'Checkout SCM'
        checkout scm
    }
    stage('Build') {
        echo 'Build'
//    openshiftBuild(bldCfg: 'spring-boot-camel-swagger-ui', showBuildLogs: 'true')
        sh "mvn clean package"
    }

    stage('Test') {
        sh "mvn test"
    }

    stage('Deploy') {
//        sh "mvn fabric8:undeploy"
//        sh "mvn fabric8:deploy -Popenshift -DskipTests"
        echo 'deploy'
        openshiftDeploy(depCfg: 'spring-boot-camel-swagger-ui')
    }
}
