FROM maven:amazoncorretto as build
COPY src .
COPY pom.xml .
CMD ["mvn", "clean", "package"]


#FROM amazoncorretto:11-alpine-jdk
FROM openjdk:11
#ARG JAR_FILE=target/*.jar
#COPY --from=build ${JAR_FILE} Visualize_Service.jar
ADD target/Visualize_Service-0.0.1-SNAPSHOT.jar Visualize_Service.jar
EXPOSE 9004
ENTRYPOINT ["java", "-jar", "Visualize_Service.jar"]