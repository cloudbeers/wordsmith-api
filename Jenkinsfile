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
    - name: docker
      image: docker:stable-dind
      command:
      - cat
      tty: true
      volumeMounts:
        - mountPath: /var/run/docker.sock
          name: docker-sock
    - name: helm
      image: lachie83/k8s-helm
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
        container('maven') {
          withMaven() {
            sh 'mvn clean deploy'
          }
        }
      }
    }
    stage('Build Docker Image') {
      steps {
        container('docker') {
          sh "docker build -t wordsmith-api:1.0.0-SNAPSHOT ."
          withCredentials([usernamePassword(credentialsId: 'bitbucket-server-credentials', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
            sh """
               docker login --username ${USERNAME} --password ${PASSWORD}
               docker push ${USERNAME}/wordsmith-api:1.0.0-SNAPSHOT
             """
           }
        }
      }
    }
    stage('Build Helm Chart') {
      steps {
        container('helm') {
          sh 'helm package wordsmith-api'
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

