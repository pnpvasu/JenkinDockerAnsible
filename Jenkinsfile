pipeline {
    agent any
       environment {
      DOCKER_TAG = getVersion()
    }
    tools { 
        maven 'maven3'
    }
    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "M2_HOME = ${M2_HOME}"
                    echo "DOCKER_TAG = ${DOCKER_TAG}"
                ''' 
            }
        }
        
         stage('SCM'){
            steps{
                git credentialsId: 'gitaccess',
                    url: 'https://github.com/mpmr28/JenkinDockerAnsible.git'
            }
        }

        stage('Maven Build'){
            steps{
                sh 'mvn clean package -Dmaven.test.skip=true'
            }
        }
     
        stage('Docker Build'){
            steps{
                sh "docker build . -t mpmrraj28/javadevops2:${DOCKER_TAG} "
            }
        }
        stage('DockerHub Push'){
            steps{
                //withDockerRegistry(credentialsId: 'docker', url: 'https://hub.docker.com/repository/docker/mpmrraj28/javadevops2') {

                sh "docker push mpmrraj28/javadevops2:${DOCKER_TAG} "
               // }
            }
        }
        
        stage('Deploy App'){
            steps{
                ansiblePlaybook credentialsId: 'devnode', disableHostKeyChecking: true, extras: "-e DOCKER_TAG=${DOCKER_TAG}",
                installation: 'Ansible', inventory: 'dev.inv', playbook: 'deploy-docker.yml'
            }
        }
    }
}
def getVersion(){
    def commitHash = sh label: '', returnStdout: true, script: 'git rev-parse --short HEAD'
    return commitHash
}
