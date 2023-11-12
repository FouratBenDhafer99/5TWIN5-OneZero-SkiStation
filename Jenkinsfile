pipeline {
    agent any
    environment {
        registry= "rayenbourguiba/skistationdevopsimage"
        registryCredential = 'DockerHub'

        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "http://172.10.0.140:8081"
        NEXUS_REPOSITORY = "nexus-repo-skistation"
        NEXUS_CREDENTIAL_ID = "nexus-user-credential"

        dockerImage = ''
    }
    stages{
        stage('Checkout GIT'){
            steps{
                echo 'Pulling...';
                git branch: 'RayenBourguiba-5TWIN5-G7',
                url: 'https://github.com/FouratBenDhafer99/5TWIN5-OneZero-SkiStation';
            }
        }
        stage('MVN package') {
            steps {
                sh 'mvn -DskipTests clean package'
            }
        }
        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }
//        stage('SonarQube Analysis') {
//            steps {
//                    sh "mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=password"
//            }
//        }
        stage('Code Coverage and SonarQube Analysis') {
            steps {
                sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install'
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=password'
            }
        }
        stage('Building Docker image') {
            steps {
                script {
                    dockerImage = docker.build rayenbourguiba + ":$BUILD_NUMBER"
                }
            }
        }
        stage('Deploy docker image') {
            steps {
                script {
                    docker.withRegistry( '', registryCredential ) {
                        dockerImage.push()
                    }
                }
            }
        }
        stage('Nexus Deployment') {
            steps {
                sh 'mvn deploy -DskipTests -DaltDeploymentRepository=deploymentRepo::default::http://172.10.0.140:8081/repository/maven-snapshots/'
            }
        }
        stage('Cleaning up') {
            steps {
                sh "docker rmi $registry:$BUILD_NUMBER"
            }
        }
    }
}
