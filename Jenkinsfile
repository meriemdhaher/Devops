pipeline {
    agent any

    stages {
        stage('Maven Build Stage') {
            steps {
                script {
                    echo "starting mvm compile"
                    sh "mvn compile"
                }
            }
        }
    }
}
