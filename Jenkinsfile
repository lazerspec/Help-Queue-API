pipeline {
    agent any

    stages {
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
    }
}