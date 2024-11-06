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
            }


            

        }






        }

        


}


