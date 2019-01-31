#!/usr/bin/groovy

pipeline {

    agent any

    stages {
        stage 'checkout scm' {
            steps {
                echo 'checkout scm'
                checkout scm
            }
        }
        stage 'build' {
            steps {
                echo 'build'
//    openshiftBuild(buildConfig: 'spring-boot-camel-swagger-ui', showBuildLogs: 'true')
                sh "mvn clean package"
            }
        }

        stage("Test") {
            steps {
                sh "mvn test"
            }
        }

        stage 'deploy' {
//        sh "mvn fabric8:undeploy"
//        sh "mvn fabric8:deploy -Popenshift -DskipTests"
            steps {
                echo 'deploy'
                openshiftDeploy(deploymentConfig: 'spring-boot-camel-swagger-ui')
            }
        }
    }

}
