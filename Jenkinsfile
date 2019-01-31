#!/usr/bin/groovy

@Library('github.com/fabric8io/fabric8-pipeline-library@master')

def canaryVersion = "1.0.${env.BUILD_NUMBER}"
def utils = new io.fabric8.Utils()
def stashName = "buildpod.${env.JOB_NAME}.${env.BUILD_NUMBER}".replace('-', '_').replace('/', '_')

mavenNode() {

    stages {

        stage 'checkout scm' {
            echo 'checkout scm'
            checkout scm
        }

        stage 'build' {

            echo 'build'
//    openshiftBuild(buildConfig: 'spring-boot-camel-swagger-ui', showBuildLogs: 'true')
            sh "mvn clean package"
        }

        stage("Test") {
            sh "mvn test"
        }

        stage 'deploy' {
//        sh "mvn fabric8:undeploy"
//        sh "mvn fabric8:deploy -Popenshift -DskipTests"
            echo 'deploy'
            openshiftDeploy(deploymentConfig: 'spring-boot-camel-swagger-ui')
        }
    }
}
