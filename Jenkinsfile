pipeline {
    agent any
    environment {
        SONAR_TOKEN = credentials('sonar_token')
        GITHUB_TOKEN = credentials('0d1bbb21-6ccb-493b-985a-eab990cbe050') // ID que vous avez donné au credential GitHub
    }
    stages {
        stage('Checkout Stage') {
            steps {
                script {
                    echo "Cloning repository"
                    // Cloning the repository
                    git url: 'https://github.com/Aziiz01/Devops.git', credentialsId: '0d1bbb21-6ccb-493b-985a-eab990cbe050'
                }
            }
        }
        stage('Maven Build Stage') {
            steps {
                script {
                    echo "Building"
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
                    echo "Starting Sonar analysis"
                    sh "mvn sonar:sonar -Dsonar.login=${SONAR_TOKEN}"
                }
            }
        }
    }
}
