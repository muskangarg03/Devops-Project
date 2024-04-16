FROM openjdk:23-jdk
EXPOSE 8080  
ADD target/springapp.jar springapp.jar
ENTRYPOINT ["java","-jar", "/springapp.jar"]
