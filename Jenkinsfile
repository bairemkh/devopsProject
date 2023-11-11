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
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'bairemkh', passwordVariable: 'bairem123')]) {
                        docker.withRegistry('https://registry.hub.docker.com', 'Docker Hub') {
                            docker.image("devops_back").push()
                        }
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