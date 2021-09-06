FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} Import_Service.jar
ENTRYPOINT ["java", "-jar", "/Import_Service.jar"]
EXPOSE 9001