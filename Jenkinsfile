pipeline {
    agent any
    environment {
	        registryCredentials = "nexus"
                registry = "http://192.168.94.31:8085/"
	        image = "192.168.94.31:8085"
		dockerHome = tool 'myDocker'
		mavenHome = tool 'myMaven'
		PATH = "$dockerHome/bin:$mavenHome/bin:$PATH"
	}
   
    stages{
       stage('Checkout') {
			steps {
				sh 'mvn --version'
				sh 'docker version'
				echo "Build"
				echo "PATH - $PATH"
				echo "BUILD_NUMBER - $env.BUILD_NUMBER"
				echo "BUILD_ID - $env.BUILD_ID"
				echo "JOB_NAME - $env.JOB_NAME"
				echo "BUILD_TAG - $env.BUILD_TAG"
				echo "BUILD_URL - $env.BUILD_URL"
			}
		}   
       stage('Build'){
            steps{
                sh 'sudo mvn clean install'
             }
          }
       stage('Build Docker Image') {
			steps {
				
				script {
					dockerImage = docker.build("${image}/smsotpms:${env.BUILD_NUMBER}")
				}

			}
        
    }
 stage('Uploading to Nexus') {
     steps{  
         script {
             docker.withRegistry( ''+registry, registryCredentials ) {
             dockerImage.push("${env.BUILD_NUMBER}")
          }
        }
     }
 }
stage('Remove Unused docker image') {
                          steps{
                             sh "docker rmi ${image}/smsotpms:$BUILD_NUMBER"
                             
       }
   }
	    
 	

    }
}

