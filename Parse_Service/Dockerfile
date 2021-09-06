FROM openjdk:11-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} Parse_Service.jar
ENTRYPOINT ["java", "-jar", "/Parse_Service.jar"]
EXPOSE 9002