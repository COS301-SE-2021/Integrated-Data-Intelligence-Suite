FROM maven:amazoncorretto as build
COPY src .
COPY pom.xml .
CMD ["mvn", "clean", "package"]


FROM amazoncorretto:11
#FROM openjdk:11
#ARG JAR_FILE=target/*.jar
#COPY --from=build ${JAR_FILE} Visualize_Service.jar
ADD target/Report_Service-0.0.1-SNAPSHOT.jar Report_Service.jar
EXPOSE 9006
ENTRYPOINT ["java", "-jar", "Report_Service.jar"]