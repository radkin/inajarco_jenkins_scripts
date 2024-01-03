
peline {
    agent any
    
    environment {
        POSTGRES_USER = "testing"
        POSTGRES_DB = "testing_db"
    }
    
    parameters {
      credentials credentialType: 'org.jenkinsci.plugins.plaincredentials.impl.StringCredentialsImpl', defaultValue: 'testingPassword', description: 'credentials for connecting to our docker postgresql instance spun up for testing', name: 'POSTGRES_PASSWORD', required: true
    }
    
    stages {
        stage('Checkout Backend') {
            steps {
                git branch: '2023-12-06-tests', url: 'https://github.com/radkin/os_backend.git'
            }
        }
        
        stage('Start Backend') {
            steps {
                sh 'docker-compose up'
            }
        }
        
        stage('Checkout oursponsors') {
            steps {
                git branch: '2023-12-13-tests', url: 'https://github.com/radkin/oursponsors.git'
            }
        }

        stage('Build') {
            steps {
              sh 'npm install'
            }
        }

        stage('Test') {
            steps {
              sh 'npm run test'
            }
        }
    }
    
    tools {
        nodejs 'node18.12.1'
    }
}

