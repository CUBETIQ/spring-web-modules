pipeline {
    agent any

    triggers {
        pollSCM '* * * * *'
    }
    stages {
        stage('Build') {
            steps {
                sh './gradlew assemble'
            }
        }
        stage('BootJar') {
            steps {
                sh './gradlew bootJar'
            }
        }
    }
}
