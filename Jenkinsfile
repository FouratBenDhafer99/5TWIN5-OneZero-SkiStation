pipeline {
    agent any
    environment {
        registry = "rayenbourguiba/skistationdevopsimage"
        registryCredential = 'dockerhub'

        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "http://172.10.0.140:8081"
        NEXUS_REPOSITORY = "nexus-repo-skistation"
        NEXUS_CREDENTIAL_ID = "nexus-user-credential"

        dockerImage = ''
    }
    stages {
        stage('Checkout Git') {
            steps {
                echo 'Pulling...'
                git branch: 'RayenBourguiba-5TWIN5-G7',
                    url: 'https://github.com/FouratBenDhafer99/5TWIN5-OneZero-SkiStation.git'
            }
        }

        stage('Build and Test') {
            steps {
                script {
                    sh 'mvn clean'
                    sh 'mvn -version'
                    sh 'java -version '
                    sh 'mvn compile'
                    sh 'mvn test'
                    sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install'
                    sh 'mvn sonar:sonar -Dsonar.host.url=http://172.10.0.140:9000 -Dsonar.login=admin -Dsonar.password=password'
                }
            }
        }

        stage('Docker Compose') {
            steps {
                sh "docker-compose up -d"
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    DOCKER_BUILDKIT = 0
                    dockerImage = docker.build registry + ":$BUILD_NUMBER"
                    echo 'Building our Docker Image rayenbourguiba/skistationdevopsimage...'
                    sh 'docker build -t rayenbourguiba/skistationdevopsimage .'
                }
            }
        }

        stage('Deploy Docker Image') {
            steps {
                script {
                    try {
                        echo "Pushing Docker image to the registry..."
                        docker.withRegistry('', registryCredential) {
                            dockerImage.push()
                        }
                        currentBuild.result = 'SUCCESS'
                    } catch (Exception e) {
                        echo "Error pushing Docker image to the registry: ${e}"
                        currentBuild.result = 'FAILURE'
                        error("Failed to push Docker image.")
                    }
                }
            }
        }

        stage('Deploy to Nexus') {
            steps {
                script {
                    def repositoryUrl = isSnapshot() ? "${NEXUS_URL}/repository/maven-snapshots/" : "${NEXUS_URL}/repository/maven-releases/"
                    try {
                        sh "mvn deploy -DskipTests -DaltDeploymentRepository=deploymentRepo::default::${repositoryUrl}"
                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        error("Maven deploy failed: ${e.message}")
                    }
                }
            }
        }

        stage('Cleanup') {
            steps {
                sh "docker rmi $registry:$BUILD_NUMBER"
            }
        }
    }
    post {
        success {
            emailext(
                subject: "Pipeline Success: 5TWIN5-OneZero-SkiStation",
                body: "Congratulations! The pipeline executed successfully.",
                to: "rayen.bourguiba@esprit.tn"
            )
        }
        failure {
            emailext(
                subject: "Pipeline Failure: 5TWIN5-OneZero-SkiStation",
                body: "Oops! Something went wrong during the pipeline execution. Please check the Jenkins logs for more details.",
                to: "rayen.bourguiba@esprit.tn"
            )
        }
    }
}

def isSnapshot() {
    return sh(script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout', returnStdout: true).trim().endsWith('-SNAPSHOT')
}
