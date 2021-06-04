pipeline {
	agent any

	environment {
		dockerImage = ''
		registry = 'sklucifer/footballservice'
		registryCredential = '94ea2bff-36c9-430e-bb45-2335cac1e502'
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