pipeline {
    agent any

    stages {
        stage('Checkout GIT') {
            steps {
                echo 'Pulling...'
                git branch: 'khouloud', url: 'https://github.com/Aziiz01/Devops.git', credentialsId: 'credentialsId'
            }
        }

        stage('Build') {
            steps {
                echo 'Building with Maven...'
                sh 'mvn compile'
            }
        }
         stage('SonareQube') {
                    steps {
                        echo 'Running sonarQube analysis...'
                        sh 'mvn sonar:sonar
                         -Dsonar.login=admin -Dsonar.password=Amoula79393*'
                    }
                }
        }}
