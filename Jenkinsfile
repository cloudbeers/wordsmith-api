pipeline {
  agent {
    kubernetes {
      label 'wordsmith-api'
      yaml """
  spec:
    containers:
    - name: jnlp
    - name: jdk
      image: openjdk:11-jdk-slim
      command:
      - cat
      tty: true
    - name: docker
      image: docker:stable-dind
      command:
      - cat
      tty: true
      volumeMounts:
        - mountPath: /var/run/docker.sock
          name: docker-sock
    - name: helm
      image: devth/helm
      command:
      - cat
      tty: true
    volumes:
      - name: docker-sock
        hostPath:
          path: /var/run/docker.sock
          type: File
"""
    }
}

  stages {
    stage('Build Java App') {
      steps {
        container('jdk') {
          withMaven() {
            sh './mvnw clean deploy'
          }
        }
      }
    }
    stage('Build Docker Image') {
      steps {
        container('docker') {
          withCredentials([usernamePassword(credentialsId: 'hub.docker.com', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
            sh """
               docker login --username ${USERNAME} --password ${PASSWORD}
               docker build -t ${USERNAME}/wordsmith-api:1.0.0-SNAPSHOT .
               docker push ${USERNAME}/wordsmith-api:1.0.0-SNAPSHOT
             """
           }
        }
      }
    }
    stage('Build Helm Chart') {
      steps {
        container('helm') {
          sh """
             helm package charts/wordsmith-api
             # helm repo add chartmuseum http://
             """
        }
      }
    }
    stage('Deploy to Staging environment') {
      steps {
        sh 'echo TBD'
      }
    }
  }
}

