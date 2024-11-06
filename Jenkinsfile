pipeline {
    agent any
    environment{
        SONAR_TOKEN=credentials('jenkins-sonar')
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
                    echo "starting mvm compile"
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
                    mvn sonar:sonar \
                      -Dsonar.login=${SONAR_TOKEN} \
                      -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                  """
                }
              }
              stage('Deploy To Nexus') {
                    steps {
                      echo 'Deploying to Nexus'
                      withCredentials([usernamePassword(credentialsId: 'nexus-credentials', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                        sh """
                          mvn deploy \
                            -Dnexus.username=${NEXUS_USER} \
                            -Dnexus.password=${NEXUS_PASS} \
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
              stage('Run Docker Container') {
                  steps {
                           script {
                                 echo 'Running Docker Container'
                                   sh "docker stop ${CONTAINER_NAME} || true && docker rm ${CONTAINER_NAME} || true"
                                   sh "docker run -d --name ${CONTAINER_NAME} -p 8089:8089 ${IMAGE_TAG}"
                                 }
                              }
                             }
    }
}
