FROM openjdk:11-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} Visualize_Service.jar
ENTRYPOINT ["java", "-jar", "/Visualize_Service.jar"]
EXPOSE 9004