FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} Registry_Service.jar
ENTRYPOINT ["java", "-jar", "/Registry_Service.jar"]
EXPOSE 8761