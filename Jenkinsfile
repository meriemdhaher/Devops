pipeline {
    agent any
    environment{
        SONAR_TOKEN=credentials('sonar_token')
    }
    stages {
        stage('Maven Build Stage') {
            steps {
                script {
                    echo "starting mvm compile"
                    sh "mvn compile"
                }
            }
        }
         stage('Sonar Static Test Stage teeeeeeestststststststttststststs') {
            steps {
                script {
                    echo "starting static test"
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
