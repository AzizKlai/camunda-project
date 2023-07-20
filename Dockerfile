FROM openjdk:17-jdk-alpine
EXPOSE 8080
ADD target/camunda-project.jar camunda-project.jar
ENTRYPOINT [ "java","-jar","/camunda-project.jar" ]