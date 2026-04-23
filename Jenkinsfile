pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                git 'https://github.com/AditiNaldurgkar/hybrid-framework.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Report') {
            steps {
                publishHTML([
                    reportDir: 'reports',
                    reportFiles: 'extent-report-jenkins.html',
                    reportName: 'Test Report Jenkins',
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: false
                ])
            }
        }
    }
}