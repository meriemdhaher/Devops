pipeline {
    agent any
    environment {
        SONAR_TOKEN = credentials('sonar_token')
    }
    stages {
        stage('Checkout Code') {
            steps {
                script {
                    echo "Checking out code from Git"
                    checkout scm
                }
            }
        }

        stage('Maven Build Stage') {
            steps {
                script {
                    echo "Starting Maven compile"
                    sh "mvn compile"
                }
            }
        }

        stage('Unit Test Stage') {
            steps {
                script {
                    echo "Starting unit tests"
                    sh "mvn test"
                }
            }
        }

        stage('Sonar Static Test Stage') {
            steps {
                script {
                    echo "Starting static analysis with SonarQube"
                    sh "mvn sonar:sonar -Dsonar.login=${SONAR_TOKEN}"
                }
            }
        }
    }
}
