## **Implementation of CI/CD Pipeline:**

A CI/CD CICD (Continuous Integration/Continuous Deployment) pipeline is a set of automated processes and tools used in software development to ensure that changes made to the codebase are systematically built, tested, and deployed.

In this project application, Spring Boot is used to develop back-end and Thymeleaf as template Engine is used to develop the front end.

**Steps to implement CICD Pipeline:**

1. **Code Development and Version Control (Git):**
   After writing the application code, store it in a version control system like Git.

2. **Continuous Integration (CI):**
   - **Trigger:** Any push or merge to the repository triggers the CI pipeline.
   - **Jenkins:** Jenkins, a popular CI/CD tool, monitors the Git repository for changes. When a change is detected, Jenkins pulls the code from the repository.
   - **Maven:** Maven is commonly used as the build automation tool. Jenkins uses Maven to compile source code, manage dependencies, run tests, and package the application.
   - **Build:** Jenkins pulls the latest code, runs Maven to build the application, and generates artifacts such as JAR files or WAR files.

3. **Dockerization:**
   - **Docker:** Docker is used to containerize the application along with its dependencies. Jenkins can utilize Docker to build Docker images containing the application.
   - **Dockerfile:** A Dockerfile is created to define the environment and steps needed to run the application. Jenkins uses this Dockerfile to build Docker images.
   - **Container Registry:** After building the Docker image, it's pushed to a container registry (DockerHub) where it's stored securely and can be accessed for deployment.

4. **Continuous Deployment (CD):**
   - **AWS EC2 Instance Configuration:** Before deploying to AWS EC2 instance, need to set up the EC2 instance, including security groups, key pairs, and other necessary configurations.
   - **Deployment to EC2:** Jenkins can deploy the Docker image to the AWS EC2 instance by connecting to it over SSH and pulling the Docker image from the container registry.
   - **Docker Installation:** Install the Docker on EC2 instance. Jenkins can SSH into the instance and install Docker to run the Docker container.
   - **Docker Container Deployment:** Jenkins runs the Docker container on the EC2 instance, exposing the necessary ports for accessing the application.

**Pipeline Script:**

```groovy
pipeline {
    agent any
    tools {
        maven "maven"
    }
    stages {
        stage("Build Maven") {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/muskangarg03/Devops-Project']])
                bat 'mvn clean install -DskipTests'
            }
        }
        stage("Build Docker Image") {
            steps {
                script {
                    bat 'docker build -t muskangarg03/devops-practical .'
                }
            }
        }
        stage("Push Image to Hub") {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerpwd', variable: 'dockerpwd')]) {
                        bat 'docker login -u muskangarg03 -p %dockerpwd%'
                        bat 'docker push muskangarg03/devops-practical'
                    }
                }
            }
        }
        stage('Testing the deployment') {
            steps {
                script {
                    echo 'Removing existing container and image...'
                    bat 'docker rm -f springapp || true' // Remove container if exists, ignore if not found
                    bat 'docker rmi -f muskangarg03/devops-practical || true' // Remove image if exists, ignore if not found
                }
                echo 'Starting a local container of the App ....'
                bat 'docker run -dit --name springapp -p 3000:8090 muskangarg03/devops-practical'
                echo 'The App is now available at Port 3000 ....'
            }
        }
    }
}
```

**Commands to install Docker and run container on EC2 Instance are as follows:**
```groovy
- Update the package index:
  sudo apt-get update
- Install docker:
  sudo apt-get install docker.io -y
- Start the Docker service:
  sudo systemctl start docker
- Enable docker service
  sudo systemctl enable docker
- Verify the docker installation
  docker --version
- Add user to Docker Group
  sudo usermod -a -G docker $(whoami)
- Activate the changes:
  newgrp docker
- Run the docker container:
  docker run -dit --name springapp -p 3000:8090 muskangarg03/devops-practical
