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
                                        // Utilisation de guillemets doubles pour la commande SonarQube
                        sh "mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=Amoula79393*"
                    }
                }

        stage('Test with JaCoCo') {
                    steps {
                        echo 'Running tests with JaCoCo...'
                        sh 'mvn test jacoco:report'
                    }
                }
                }


        }
