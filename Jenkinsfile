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
                        sh "echo -n ${password} | sudo docker build -t devops_back ."
                        }
                        }
        }
        stage('Push Docker Images to Docker Hub') {
                        steps {
                                    sh "docker login -u bairemkh -p bairem123"
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