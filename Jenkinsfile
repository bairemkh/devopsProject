pipeline {
    agent any
    stages {
    stage('Test') {
                steps {
                    sh 'mvn test'
                    step([$class: 'JaCoCoPublisher', sourcePattern: '**/target/*.exec'])

                }
            }
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}