#!/usr/bin/groovy

// https://github.com/openshift/jenkins-plugin
// https://jenkins.io/blog/2017/02/07/declarative-maven-project/

library identifier: "pipeline-library@master",
        retriever: modernSCM(
                [
                        $class: "GitSCMSource",
                        remote: "https://github.com/redhat-cop/pipeline-library.git"
                ]
        )

@Library('github.com/fabric8io/fabric8-pipeline-library@master')
def setupScript = null

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
