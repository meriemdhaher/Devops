pipeline {
    agent any
    environment {
        SONAR_TOKEN = credentials('jenkins-sonar')
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
stage('Maven Compile') {
            steps {
                script {
                    echo "Starting Maven compile"
                 //   sh "mvn compile"
                }
            }
        }

stage('Maven Test') {
            steps {
                script {
                    echo "Starting Maven test"
                 //   sh "mvn test"
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
               echo 'Building Docker Image'
               sh 'docker build -t gestion-station-ski .'
             }
           }

           stage('Start Docker Compose') {
             steps {
               echo 'Starting Docker Compose for Integration Tests'
               sh 'docker compose up -d'
             }
           }


    post {
        always {
            script {
                emailext(
                    subject: "Jenkins Build - ${env.BUILD_NUMBER}",
                    body: "Stage Successful.",
                    mimeType: 'text/html',
                    to: 'mohamedaziz.nacib@esprit.tn'
                )
            }
        }
        success {
            echo 'Build Successful!!!'
        }
        failure {
            script {
                emailext(
                    to: 'mohamedaziz.nacib@esprit.tn',
                    subject: "Build Failure in Pipeline: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                    body: "The Jenkins pipeline encountered an error."
                )
            }
        }
    }

}
