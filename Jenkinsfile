pipeline {
    agent any
    stages {
    stage('Test') {
                steps {
                    sh 'mvn test'
                }
            }
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Build Docker Images') {
                        steps {
                        script {
                        def password = 'changeme'
                        sh "sudo -S docker build -t devops_back ."
                        sh "echo -n ${password} "
                        }
                        }
        }
        stage('Push Docker Images to Docker Hub') {
                        steps {
                                    sh "echo bairem123 | docker login -u bairemkh --password-stdin"
                                    sh 'docker push devops_back'
                        }
        }
        stage('Deploy with Docker Compose') {
                        steps {
                                    sh 'docker-compose up -d'
                        }
                }
    }
}