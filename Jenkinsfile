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
                        sh 'docker ps'
                        sh 'docker-compose up -d'
                        }
                        }
        }
        stage('Configure Prometheus') {
                            steps {
                                script {
                                sh 'docker run -d --name prometheus -p 9090:9090 prom/prometheus'
                                sh 'docker exec prometheus cat /etc/prometheus/prometheus.yml'
                                }
                            }
                        }
        /*stage('SonarQube Analysis') {
                    steps {
                        script {
                            def scannerHome = tool 'SonarQube Scanner'
                            withSonarQubeEnv('SonarQube Server') {
                                sh "${scannerHome}/bin/sonar-scanner"
                            }
                        }
                    }
        }*/
    }
    post {
            success {
                emailext subject: 'Jenkins Pipeline - Success',
                          body: 'The Jenkins pipeline has completed successfully.',
                          to: 'bairem.khedhri@esprit.tn'
            }

            failure {
                emailext subject: 'Jenkins Pipeline - Failure',
                          body: 'The Jenkins pipeline has failed. Please check the build logs.',
                          to: 'bairem.khedhri@esprit.tn'
            }
        }
}