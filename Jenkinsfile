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
                    echo "starting mvm compile teeeeeeestststststststttststststs"
                    sh "mvn compile"
                }
            }
        }

        stage('Unit Tests') {
                       steps {
                         echo 'Running Unit Tests with Coverage'
                         sh 'mvn -Dtest=SkierServicesImplTest test jacoco:report'
                       }
                       post {
                         always {
                           junit '*/target/surefire-reports/TEST-.xml'
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

    }
}
