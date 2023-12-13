pipeline {
    agent any
    
    environment {
        POSTGRES_USER = "testing"
        POSTGRES_DB = "testing_db"
    }
    
    parameters {
      credentials credentialType: 'org.jenkinsci.plugins.plaincredentials.impl.StringCredentialsImpl', defaultValue: 'testingPassword', description: 'credentials for connecting to our docker postgresql instance spun up for testing', name: 'POSTGRES_PASSWORD', required: true
    }
    
    stages {
        stage('Checkout') {
            steps {
                git branch: '2023-12-06-tests', url: 'https://github.com/radkin/os_backend.git'
            }
        }
        
        stage('Postgresql') {
            steps {
                sh 'docker-compose up -d dbpostgresql'
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
