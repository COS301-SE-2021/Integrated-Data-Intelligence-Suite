FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} Analyse_Service.jar
ENTRYPOINT ["java", "-jar", "/Analyse_Service.jar"]
EXPOSE 9003