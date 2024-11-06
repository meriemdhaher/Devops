pipeline {
    agent any

    environment {
        SONAR_TOKEN = credentials('sonar_token')
        NEXUS_CREDENTIALS = credentials('nexus')
        DOCKER_REPO = 'devops-project'
        CONTAINER_NAME = "devops-project-container"
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
                    sh "mvn test -DskipTests=false"
                    sh "if [ -d 'target/site/jacoco' ]; then ls target/site/jacoco; else echo 'Directory target/site/jacoco does not exist'; fi || true"
                }
            }
        }

        stage('Sonar Static Test Stage') {
            steps {
                script {
                    echo "Starting static analysis with SonarQube"
                    withCredentials([string(credentialsId: 'sonar_token', variable: 'SONAR_TOKEN')]) {
                        sh "mvn sonar:sonar -Dsonar.login=${SONAR_TOKEN}"
                    }
                }
            }
        }
        
        stage('Deploy To Nexus') {
            steps {
                echo 'Deploying to Nexus'
                withCredentials([usernamePassword(credentialsId: 'nexus', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                    sh '''
                        mvn deploy \
                        -Dnexus.username=${NEXUS_USER} \
                        -Dnexus.password=${NEXUS_PASS} \
                        -DskipTests
                    '''
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker Image'
                sh "docker build -t ${DOCKER_REPO} ."
            }
        }
        

        stage('Run Docker Compose') {
            steps {
                echo 'Starting Services with Docker Compose'
                sh 'docker compose down || true'  // Stop any previous instances
                sh 'docker compose up -d --build'
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
    }
}
