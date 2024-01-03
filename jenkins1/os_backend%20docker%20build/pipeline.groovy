pipeline {
    agent any
    
    environment {
        registry = "radkin/os_backend"
        registryCredential = 'jenkins docker'
        dockerImage = ''
    } 
    
    stages {
        stage('Checkout') {
            steps {
                git branch: '2023-12-06-tests', url: 'https://github.com/radkin/os_backend.git'
            }
        }
        
        stage('Build') {
            steps {
              sh 'mvn clean package -DskipTests'
            }
        }

        stage('Building Image') {
            steps {
                script {
                    dockerImage = docker.build registry + ":$BUILD_NUMBER"
                }
            }
        }
        stage('Deploy Image') {
          steps{
            script {
              docker.withRegistry( '', registryCredential ) {
                dockerImage.push()
              }
            }
          }
        }
        stage('Remove Unused docker image') {
          steps{
            sh "docker rmi $registry:$BUILD_NUMBER"
          }
        }
    }
    
    tools {
        jdk 'jdk19'
    }
}

