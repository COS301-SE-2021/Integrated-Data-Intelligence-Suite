FROM maven:amazoncorretto as build
COPY src .
COPY pom.xml .
CMD ["mvn", "clean", "package"]


#FROM amazoncorretto:11-alpine-jdk
FROM openjdk:11
#ARG JAR_FILE=target/*.jar
#COPY --from=build ${JAR_FILE} Parse_Service.jar
ADD target/Parse_Service-0.0.1-SNAPSHOT.jar Parse_Service.jar
EXPOSE 9002
ENTRYPOINT ["java", "-jar", "Parse_Service.jar"]