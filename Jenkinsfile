pipeline {
	agent any

	environment {
		dockerImage = ''
		registry = 'sklucifer/footballservice'
		registryCredential = ''
	}

	tools {
		maven "3.5.4"
	}

	stages {
		stage("Build") {
			steps {
				sh "mvn -version"
				sh "mvn clean install"
			}
		}

		stage("Build Docker Image") {
			steps {
				script {
					dockerImage = docker.build registry
				}
			}
		}	

		stage("Uploading Docker Image") {
			steps {
				script {
					 docker.withRegistry( '', registryCredential ) {
						dockerImage.push()
					 }
				}
			}
		}
	}

	post {
		always {
			cleanWs()
		}
	}
}