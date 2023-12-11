pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: '2023-12-06-tests', url: 'https://github.com/radkin/os_backend.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn clean test'
            }
        }
    }
    
    tools {
        jdk 'jdk19'
    }
}

