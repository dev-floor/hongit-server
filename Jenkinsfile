node {
    stage ('clone') {
        git branch: 'feature/98', url: 'https://github.com/dev-floor/hongit-server'
    }
    stage('build') {
        sh './gradlew hongit-api:clean hongit-api:build && ./gradlew hongit-core:clean hongit-core:build'
    }
    stage('docker') {
      sh 'cd docker && docker-compose up -d'
    }
}