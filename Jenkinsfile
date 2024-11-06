pipeline {
    agent any
    environment {
        SONAR_TOKEN = credentials('jenkins-sonar')
        IMAGE_TAG = "dev"
        CONTAINER_NAME = "dev-container"
    }

    stages {
        stage('Checkout Code') {
            steps {
                script {
                    checkout scm
                    echo "Checked out latest code"
                }
            }
        }

        stage('Maven Build Stage') {
            steps {
                script {
                    echo "Starting Maven compile"
                    sh "mvn clean package -DskipTests"
                }
            }
        }

        stage('Unit Tests') {
            steps {
                echo 'Running Unit Tests with Coverage'
                sh 'mvn -Dtest=SkierServiceImplTest test jacoco:report'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    jacoco execPattern: '**/target/jacoco.exec'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo 'Static Analysis with SonarQube'
                sh """
                    mvn sonar:sonar \\
                      -Dsonar.login=${SONAR_TOKEN} \\
                      -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                """
            }
        }

        stage('Deploy To Nexus') {
            steps {
                echo 'Deploying to Nexus'
                withCredentials([usernamePassword(credentialsId: 'nexus-credentials', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                    sh """
                        mvn deploy \\
                          -Dnexus.username=${NEXUS_USER} \\
                          -Dnexus.password=${NEXUS_PASS} \\
                          -DskipTests
                    """
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo 'Building Docker Image'
                    sh "docker build -t ${IMAGE_TAG} ."
                }
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
}
