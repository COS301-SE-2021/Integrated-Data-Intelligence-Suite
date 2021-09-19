FROM maven:amazoncorretto as build
COPY src .
COPY pom.xml .
CMD ["mvn","-f", "pom.xml", "clean", "package"]


FROM amazoncorretto:11-alpine-jdk
#ARG JAR_FILE=target/*.jar
#COPY --from=build ${JAR_FILE} Registry_Service.jar
ADD target/Registry_Service-0.0.1-SNAPSHOT.jar Registry_Service.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "Registry_Service.jar"]