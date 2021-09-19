FROM maven:amazoncorretto as build
COPY src .
COPY pom.xml .
CMD ["mvn", "clean", "package"]


FROM amazoncorretto:11-alpine-jdk
#ARG JAR_FILE=target/*.jar
#COPY --from=build ${JAR_FILE} Gateway_Service.jar
ADD target/Gateway_Service-0.0.1-SNAPSHOT.jar Gateway_Service.jar
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "Gateway_Service.jar"]