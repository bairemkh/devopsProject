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
        stage('Push to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-login',usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                            sh 'docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD'
                            sh 'docker tag devops_back devops_back:latest'
                            sh "docker push devops_back"
                    }
                }
            }
        }
        stage('Deploy with Docker Compose') {
                        steps {
                                    sh 'docker-compose up -d'
                        }
                }
    }
}