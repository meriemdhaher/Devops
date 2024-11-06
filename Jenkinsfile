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

  stage  ('Quality Gate status'){

            steps {
                script{
                    
                waitForQualityGate abortPipeline: false, credentialsId: 'jenkins' {
             
                }
   
                    }
            }
            

        }




        }

        


}


