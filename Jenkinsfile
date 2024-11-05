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
         stage('Sonar Static Test Stage teeeeeeestststststststttststststs') {
            steps {
                script {
                    echo "starting static test teeeeeeestststststststttststststs"
                    sh "mvn sonar:sonar -Dsonar.login=${SONAR_TOKEN}"
                }
            }
        }
         stage('Unit Test Stage') {
                    steps {
                        script {
                            echo "starting unit test"
                            sh "mvn test"
                        }
                    }
                }
    }
}
