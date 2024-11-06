pipeline {

        agent any 

        stages {
        
        stage  ('Git Checkout'){

            steps {
                    git branch: 'ghassen', credentialsId: 'devops', url: 'https://github.com/Aziiz01/Devops.git'
            }

        }

          stage  ('Unit testing '){

            steps {
                   sh 'mvn test'
            }}

        stage  ('Maven build '){

            steps {
                   sh 'mvn clean install'
            }
            

        }
  stage  ('Static code analysis '){

            steps {
                script{

                withSonarQubeEnv(credentialsId: 'jenkins') {
                sh 'mvn clean package sonar:sonar'
                }
   
                    }
            }
            

        }
          stage  ('Upload war file to nexus '){

            steps {
                script{
                        nexusArtifactUploader artifacts: [[artifactId: 'spring-boot-starter-parent', classifier: '', file: 'target/StationSki.jar', type: 'jar']], credentialsId: 'nexus-auth', groupId: 'tn.esprit.spring', nexusUrl: 'localhost:8082/', nexusVersion: 'nexus3', protocol: 'http', repository: 'ski-release', version: '1.0'
               
   
                    }
            }
            

        }

          stage  ('Docker image build '){

            steps {
                script{
                        sh 'docker image build -t $JOB_NAME:v1.$BUILD_ID'
                        sh 'docker image tag  $JOB_NAME:v1.$BUILD_ID  ghassen112/$JOB_NAME:v1.$BUILD_ID'
                        sh 'docker image tag  $JOB_NAME:v1.$BUILD_ID  ghassen112/$JOB_NAME:latest'

                    }
            }
            

        }






        }

        


}


