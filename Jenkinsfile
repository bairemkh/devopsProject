pipeline {
    agent any
    stages {
    stage('Test') {
                steps {
                    sh 'mvn --version'
                    sh 'mvn clean install'
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
        stage('Push to Docker Hub') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'docker-token', variable: 'DOCKERHUB_TOKEN')]) {
                            sh 'echo $DOCKERHUB_TOKEN | docker login -u bairemkh --password-stdin'
                            sh 'docker tag devops_back bairemkh/devops_back:latest'
                            sh "docker push bairemkh/devops_back:latest"
                    }
                }
            }
        }
        stage('Deploy with Docker Compose') {
                        steps {
                        script {
                        sh 'docker-compose up -d'
                        sh 'sudo docker pull prom/prometheus'
                        sh 'docker-compose ps'
                        }
                        }
                }
    }
}