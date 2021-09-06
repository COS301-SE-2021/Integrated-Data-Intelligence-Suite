FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} User_Service.jar
ENTRYPOINT ["java", "-jar", "/User_Service.jar"]
EXPOSE 9005