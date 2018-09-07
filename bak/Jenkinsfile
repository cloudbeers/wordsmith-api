pipeline {
  agent {
    kubernetes {
      label 'wordsmith-api'
      yaml """
  spec:
    containers:
    - name: jnlp
    - name: maven
      image: maven:3.5.4-jdk-8-alpine
      command:
      - cat
      tty: true
      """
    }
}

  stages {
    stage('Build') {
      steps {
        container('maven') {
          withMaven() {
            sh 'mvn clean deploy'
          }
        }
      }
    }
    stage('Upload artifact') {
      steps {
        sh 'echo TBD'
      }
    }
    stage('Deploy to Staging environment') {
      steps {
        sh 'echo TBD'
      }
    }
  }
}

