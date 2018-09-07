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
             # create helm chart version
             helm package charts/wordsmith-api
             # upload helm chart
             curl --data-binary "@wordsmith-api-1.0.0-SNAPSHOT.tgz" http://chartmuseum-chartmuseum.core.svc.cluster.local:8080/api/charts
             """
        }
      }
    }
    stage('Deploy to Staging environment') {
      steps {
        container('helm') {
          sh """
             helm init --client-only
             helm repo add wordsmith http://chartmuseum-chartmuseum.core.svc.cluster.local:8080
             helm repo update
             helm install --namespace staging --name wordsmith-api wordsmith/wordsmith-api --version 1.0.0-SNAPSHOT
             """
        }      }
    }
  }
}

