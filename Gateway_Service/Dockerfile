FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} Gateway_Service.jar
ENTRYPOINT ["java", "-jar", "/Gateway_Service.jar"]
EXPOSE 9000