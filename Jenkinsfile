pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'stage of building'
                // Exemple pour Maven
                // sh "mvn -Dmaven.test.failure.ignore=true clean package"
            }
        }
        stage('test') {
            steps {
                echo 'stage of testing'
            }
        }
    }
}
