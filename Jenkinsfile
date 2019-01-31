#!/usr/bin/groovy

@Library('github.com/fabric8io/fabric8-pipeline-library@master')

mavenNode() {
    checkout scm
    stage 'build' {
//    openshiftBuild(buildConfig: 'spring-boot-camel-swagger-ui', showBuildLogs: 'true')
        sh "mvn clean package"
    }

    stage("Test") {
        sh "mvn test"
    }

    stage 'deploy' {
//        sh "mvn fabric8:undeploy"
//        sh "mvn fabric8:deploy -Popenshift -DskipTests"
        openshiftDeploy(deploymentConfig: 'spring-boot-camel-swagger-ui')
    }
}
