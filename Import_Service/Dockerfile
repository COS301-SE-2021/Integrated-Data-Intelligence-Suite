FROM maven:amazoncorretto as build
COPY src .
COPY pom.xml .
CMD ["mvn", "clean", "package"]



FROM amazoncorretto:11
#FROM openjdk:11
#ARG JAR_FILE=target/*.jar
#COPY --from=build ${JAR_FILE} Import_Service.jar
ADD target/Import_Service-0.0.1-SNAPSHOT.jar Import_Service.jar
EXPOSE 9001
ENTRYPOINT ["java", "-jar", "Import_Service.jar"]