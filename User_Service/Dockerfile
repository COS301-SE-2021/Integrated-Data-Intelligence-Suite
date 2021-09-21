FROM maven:amazoncorretto as build
COPY src .
COPY pom.xml .
CMD ["mvn", "clean", "package"]


#FROM amazoncorretto:11-alpine-jdk
FROM openjdk:11
#ARG JAR_FILE=target/*.jar
#COPY --from=build ${JAR_FILE} User_Service.jar
ADD target/User_Service-0.0.1-SNAPSHOT.jar User_Service.jar
EXPOSE 9005
ENTRYPOINT ["java", "-jar", "User_Service.jar"]