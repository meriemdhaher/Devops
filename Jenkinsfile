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
                    git branch: 'meriem', url: 'https://github.com/Aziiz01/Devops.git'
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
