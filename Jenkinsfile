pipeline {
    agent any
    tools {
        maven 'maven'
    }
     environment {
        IMAGE_NAME = 'anaslahboub/agile-app:1.2'
    }


    stages {
        stage('Build') {
            steps {
                echo 'stage of building'

                sh 'mvn clean package -DskipTests'
            }
        }
        stage('Build image') {
            steps {
                 script {
                    echo 'stage of building diocker image'
                    withCredentials([string(credentialsId: 'docker_hub_token', variable: 'DOCKERHUB_TOKEN')]) {
                        sh 'docker build -t $IMAGE_NAME .'
                        sh 'echo "$DOCKERHUB_TOKEN" | docker login -u anaslahboub --password-stdin'
                        sh 'docker push $IMAGE_NAME'
                    }
                 }

            }
        }
        stage('test') {
            steps {
                echo 'stage of testing'
            }
        }
    }
}
