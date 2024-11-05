pipeline {
    agent any
    environment {
        SONAR_TOKEN = credentials('sonar_token')
        NEXUS_CREDENTIALS = credentials('nexus')  // Ajout des identifiants Nexus
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
        
        stage('Deploy to Nexus') {
            steps {
                script {
                    echo "Deploying artifact to Nexus"
                    sh """
                    mvn deploy:deploy-file -DrepositoryId=nexus \
                    -Durl=http://nexus-server-url/repository/maven-releases/ \
                    -Dfile=path/to/your/artifact.jar \
                    -DgroupId=your.group.id \
                    -DartifactId=your-artifact-id \
                    -Dversion=your-version \
                    -Dpackaging=jar \
                    -Dusername=${NEXUS_CREDENTIALS_USR} \
                    -Dpassword=${NEXUS_CREDENTIALS_PSW}
                    """
                }
            }
        }
    }
}
