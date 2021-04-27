pipeline{
    agent any

    environment {
      DOCKER_TAG = getVersion()
    }

    stages{
        stage('SCM'){
            steps{
                git credentialsId: 'github',
                    url: 'https://github.com/TechiePlayer/JenkinDockerAnsible'
            }
        }

        stage('Maven Build'){
            steps{
                sh "mvn clean package"
            }
        }
        stage('Docker Build'){
            steps{
                sh "docker build . -t pclpjava/rpcapp:${DOCKER_TAG} "
            }
        }
        stage('DockerHub Push'){
            steps{
                sh "docker login -u pclpjava -p xxxxx"
                sh "docker push pclpjava/rpcapp:${DOCKER_TAG} "
            }
        }
        stage('Docker Deploy'){
            steps{
                ansiblePlaybook credentialsId: 'Apr24_1', disableHostKeyChecking: true, extras: "-e DOCKER_TAG=${DOCKER_TAG}", installation: 'Ansible', inventory: 'dev.inv', playbook: 'deploy-docker.yml'
            }
        }
    }
}

def getVersion(){
    def commitHash = sh label: '', returnStdout: true, script: 'git rev-parse --short HEAD'
    return commitHash
}