#!/usr/bin/groovy

// https://github.com/openshift/jenkins-plugin

@Library('github.com/fabric8io/fabric8-pipeline-library@master')
def setupScript = null

pipeline {

    agent any

    stages {
        stage('Checkout scm') {
            steps {
                echo 'checkout scm'
                checkout scm
            }
        }
        stage('Build') {
            steps {
                echo 'build'
//    openshiftBuild(bldCfg: 'spring-boot-camel-swagger-ui', showBuildLogs: 'true')
                sh "mvn clean package"
            }
        }

        stage('Test') {
            steps {
                sh "mvn test"
            }
        }

        stage('Deploy') {
//        sh "mvn fabric8:undeploy"
//        sh "mvn fabric8:deploy -Popenshift -DskipTests"
            steps {
                echo 'deploy'
                openshiftDeploy(depCfg: 'spring-boot-camel-swagger-ui')
            }
        }
    }

}
