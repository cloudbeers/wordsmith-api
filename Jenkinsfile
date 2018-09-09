pipeline {
  agent {
    kubernetes {
      label 'wordsmith-api'
      yaml """
  spec:
    containers:
    - name: jnlp
    - name: jdk
      image: openjdk:8-jdk
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
  options {
    buildDiscarder(logRotator(numToKeepStr: '5'))
    disableConcurrentBuilds()
  }
  stages {
    stage('Build Java App') {
      steps {
        container('jdk') {
          withMaven(mavenOpts: '-Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn') {
            sh './mvnw clean deploy'
          }
        }
      }
    }
    stage('Build Docker Image') {
      environment {
        DOCKER_HUB_CREDS = credentials('hub.docker.com')
      }
      steps {
        container('docker') {
          script {
            APPLICATION_VERSION = readFile("target/VERSION")
          }
          sh """
             docker login --username ${DOCKER_HUB_CREDS_USR} --password ${DOCKER_HUB_CREDS_PSW}
             docker build -t ${DOCKER_HUB_CREDS_USR}/wordsmith-api:${APPLICATION_VERSION} .
             docker push ${DOCKER_HUB_CREDS_USR}/wordsmith-api:${APPLICATION_VERSION}
           """
        }
      }
    }
    stage('Build Helm Chart') {
      steps {
        container('helm') {
          script {
            APPLICATION_VERSION = readFile("target/VERSION")
          }
          sh """
             # create helm chart version
             helm package target/charts/wordsmith-api
             # upload helm chart
             curl --data-binary "@wordsmith-api-${APPLICATION_VERSION}.tgz" http://chartmuseum-chartmuseum.core.svc.cluster.local:8080/api/charts
             """
        }
      }
    }
    stage('Deploy to Preview Environment') {
      environment {
         PG_SQL_CREDS = credentials('postgresql.preview')
         PG_SQL_JDBC_URL = 'jdbc:postgresql://wordsmith-preview.ca3tifbqfpuf.us-east-1.rds.amazonaws.com:5432/wordsmith'
         APP_HOST = 'api.preview.wordsmith.beescloud.com'
      }
      steps {
        container('helm') {
          script {
            APPLICATION_VERSION = readFile("target/VERSION")
          }
          sh """

             helm init --client-only
             helm repo add wordsmith http://chartmuseum-chartmuseum.core.svc.cluster.local:8080
             helm repo update

             helm upgrade wordsmith-api-preview wordsmith/wordsmith-api --version ${APPLICATION_VERSION} --install --namespace preview --wait \
                --set ingress.hosts[0]=${APP_HOST},database.username=${PG_SQL_CREDS_USR},database.password=${PG_SQL_CREDS_PSW},database.url=${PG_SQL_JDBC_URL},image.pullPolicy=Always

             kubectl describe deployment wordsmith-api-preview-wordsmith-api --namespace preview
             echo "FIXME fix ingress"
             kubectl get ingress wordsmith-api-preview-wordsmith-api || true
            """
        } // container
       } // steps
    } // stage
  } // stages
}