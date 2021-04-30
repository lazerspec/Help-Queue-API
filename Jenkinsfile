pipeline {
    agent any

    stages {
        stage('Docker login') {
           steps {
                sh "echo Logging into Docker..."
                 sh "ls"
                 sh "sudo docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASS}"
               }
            }
        stage('Docker Build') {
            steps {
               sh "echo Building the application..."
               sh "ls"
               sh "sudo docker build -t parvir/ticket-api:latest ."
            }
        }
        stage('Deploy to Dockerhub') {
            steps {
            sh "echo Deploying to the docker hub"
            sh "sudo docker push parvir/ticket-api:latest"
            }
        }
        stage('Delete image from server') {
             steps {
                 sh "echo Deleting image locally"
                  sh "sudo docker image rm parvir/ticket-api"
                  }
             }
         stage('Environment for kubernetes') {
              steps {
                   sh "echo Setting secrets for kubernetes"
                   sh "sed 's/{{username}}/${DB_USERNAME}/g;s/{{password}}/${DB_PASS}/g;s/{{rdsEndpoint}}/${DB_ENDPOINT}/g' rds-credentials.yaml | ./kubectl apply -f -"
         }
       }
    }
}