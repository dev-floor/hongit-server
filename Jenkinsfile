node {
    stage ('clone') {
        git branch: 'feature/98', url: 'https://github.com/dev-floor/hongit-server'
    }
    stage('build') {
        sh './gradlew hongit-api:clean hongit-api:build && ./gradlew hongit-core:clean hongit-core:build'
    }
    stage('verify') {
          when {
            // Only test when cloning from ssh protocol URL
            // Submodule is defined with ssh protocol URL
            expression { scm.userRemoteConfigs[0].url ==~ /JoonBro@github.com:.*/ }
          }
          steps {
            logContains([expectedRegEx: ".*Found:.*JENKINS-57936.*",
                   failureMessage: "Missing submodule README contents."])
          }
    }
    stage('submodule'){
        sh 'git submodule init'
        sh 'git submodule update'
    }
    stage('docker') {
        sh 'cd docker && docker-compose up -d'
    }
}
