pipeline {
    agent any
    environment {
        SONAR_TOKEN = credentials('jenkins-sonar')
        IMAGE_TAG = "dev"
        CONTAINER_NAME = "dev-container"
        DOCKER_REPO = 'docker.io/aziz0001/dev'
        DOCKER_CREDENTIALS_ID = 'dockerhub-credentials'
        DOCKERHUB_USERNAME = 'aziz0001'
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

       stage('Push Docker Image') {
           steps {
               script {
                   echo 'Pushing Docker Image to Docker Hub'
                   withCredentials([usernamePassword(credentialsId: DOCKER_CREDENTIALS_ID, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                       sh "echo $DOCKER_PASS | docker login docker.io -u $DOCKER_USER --password-stdin"
                   }
                   sh "docker tag ${IMAGE_TAG} ${DOCKER_REPO}:${IMAGE_TAG}"
                   sh "docker push ${DOCKER_REPO}:${IMAGE_TAG}"
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
         stage('Start Prometheus and Grafana') {
                    steps {
                        echo 'Starting Prometheus and Grafana for monitoring'
                        sh 'docker compose up -d prometheus grafana'
                    }
                }

                stage('Verify Prometheus and Grafana') {
                    steps {
                        script {
                            echo 'Verifying Prometheus and Grafana services'
                            sh 'curl -s http://localhost:9090 | grep "Prometheus Time Series Collection" || echo "Prometheus not ready"'
                            sh 'curl -s http://localhost:3000 | grep "Grafana" || echo "Grafana not ready"'
                        }
                    }
                }
    }
}
