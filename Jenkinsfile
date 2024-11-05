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
                            // Utilisation de credentials Jenkins
                                    withCredentials([usernamePassword(credentialsId: 'sonarqube-credentials', passwordVariable: 'SONAR_PASSWORD', usernameVariable: 'SONAR_USERNAME')]) {
                                        // Utilisation des variables d'environnement pour le login et le mot de passe
                                        sh "mvn sonar:sonar -Dsonar.login=${SONAR_USERNAME} -Dsonar.password=${SONAR_PASSWORD}"
                                    }
                    }
                }

        stage('Test with JaCoCo') {
                    steps {
                        echo 'Running tests with JaCoCo...'
                        sh 'mvn test jacoco:report'
                    }
                }

stage('Deploy to Nexus') {
    steps {
        echo 'Deploying to Nexus...'
        sh """
            mvn deploy -X \
            -DaltDeploymentRepository=deploymentRepo::default::http://localhost:8081/repository/pistereporelease/ \
            -DdeploymentRepo.username=admin \
             -DdeploymentRepo.password=nexus
        """
    }
}


}

        }