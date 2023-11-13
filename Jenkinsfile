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
                    withCredentials([string(credentialsId: 'docker-login',variable: 'bairem')]) {
                            sh 'docker login -u bairemkh -p $bairem'
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