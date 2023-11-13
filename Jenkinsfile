def FAILED_STAGE
pipeline {
    agent any
    environment {
        registry= "fouratbendhafer11/5twin5-onezero-skistation"
        registryCredential = 'dockerhub'
        dockerImage = ''

        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "http://192.168.1.15:8081"
        NEXUS_REPOSITORY = "nexus-repo-skistation"
        NEXUS_CREDENTIAL_ID = "nexus-user-credential"
    }
    stages{
        stage('Checkout GIT'){
            steps{
                script{
                    FAILED_STAGE=env.STAGE_NAME;
                    echo 'Pulling...';
                    git branch: 'FouratBenDhafer-5TWIN5-G7',
                    url: 'https://github.com/FouratBenDhafer99/5TWIN5-OneZero-SkiStation.git';
                }
            }
        }
        stage('MVN package') {
            steps {
                script{
                    FAILED_STAGE=env.STAGE_NAME
                    sh 'mvn -DskipTests clean package'
                }
            }
        }
        stage('Unit Tests') {
            steps {
                script{
                    FAILED_STAGE=env.STAGE_NAME
                    sh 'mvn test'
                }
            }
        }
        stage('MVN SonarQube') {
            steps {
                script {
                    FAILED_STAGE=env.STAGE_NAME
                    sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install'
                    sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=adminn'
                }
            }
        }
        stage('Nexus Deployment') {
            steps {
                script {
                    FAILED_STAGE=env.STAGE_NAME
                    def repositoryUrl = ''
                    if (isSnapshot()) {
                        repositoryUrl = "${NEXUS_URL}/repository/maven-snapshots/"
                    } else {
                        repositoryUrl = "${NEXUS_URL}/repository/maven-releases/"
                    }
                    try {
                        sh "mvn deploy -DskipTests -DaltDeploymentRepository=deploymentRepo::default::${repositoryUrl}"
                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        error("Maven deploy failed: ${e.message}")
                    }
                }
            }
        }
        stage('Building Docker image') {
            steps {
                script {
                    FAILED_STAGE=env.STAGE_NAME
                    dockerImage = docker.build registry + ":$BUILD_NUMBER"
                }
            }
        }
        stage('Deploy docker image') {
            steps {
                script {
                    FAILED_STAGE=env.STAGE_NAME
                    docker.withRegistry( '', registryCredential ) {
                        dockerImage.push()
                    }
                }
            }
        }
        stage('Docker compose') {
            steps {
                script{
                    FAILED_STAGE=env.STAGE_NAME
                    sh "docker-compose up -d"
                }
            }
        }
        stage('Cleaning up') {
            steps {
                script{
                    FAILED_STAGE=env.STAGE_NAME
                    sh "docker rmi $registry:$BUILD_NUMBER"
                }
            }
        }
    }
    post {
        success {
            emailext(
                    subject: "Pipeline Success: 5TWIN5-OneZero-SkiStation",
                    body: "Congratulations! The pipeline executed successfully.",
                    to: "fourat.bendhafer@esprit.tn"
            )
        }
        failure {
            emailext(
                    subject: "Pipeline Failure: 5TWIN5-OneZero-SkiStation",
                    body: "Oops! Something went wrong during the pipeline execution: ${FAILED_STAGE}.Please check the Jenkins logs for more details.",
                    to: "fourat.bendhafer@esprit.tn"
            )
        }
    }
}
def isSnapshot() {
    return sh(script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout', returnStdout: true).trim().endsWith('-SNAPSHOT')
}