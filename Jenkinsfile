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
        steps {
            sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
        }
    }

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
