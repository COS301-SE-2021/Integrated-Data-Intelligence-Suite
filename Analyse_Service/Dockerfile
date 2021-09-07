FROM maven:amazoncorretto as build
COPY src .
COPY pom.xml .
CMD ["mvn", "clean", "package"]
#RUN mvn -f pom.xml clean package/install
#mvn spring-boot:run


FROM amazoncorretto:11-alpine-jdk
#ARG JAR_FILE=target/*.jar
#COPY --from=build ${JAR_FILE} Analyse_Service.jar
ADD target/Analyse_Service-0.0.1-SNAPSHOT.jar Analyse_Service.jar
EXPOSE 9003
ENTRYPOINT ["java", "-jar", "Analyse_Service.jar"]

#{
#ARG DEPENDENCY=target/dependency
#COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY ${DEPENDENCY}/META-INF /app/META-INF
#COPY ${DEPENDENCY}/BOOT-INF/classes /app
#ENTRYPOINT ["java","-cp","app:app/lib/*","Registry_Service.Application"]
#}


#3.9.6-alpine
FROM python:3.7.0
RUN pip install mlflow==1.20.1
ENTRYPOINT ["mlflow", "server"]
CMD ["mlflow", "ui"]


#FROM openjdk:11-jdk-alpine
#FROM amazoncorretto:11-alpine-jdk
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} Analyse_Service.jar
#ENTRYPOINT ["java", "-jar", "Analyse_Service.jar"]
#EXPOSE 9003
#CMD ["mlflow", "ui"]



#FROM openjdk:16-alpine3.13
#FROM amazoncorretto:11-alpine-jdk
#WORKDIR /app
#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#RUN ./mvnw dependency:go-offline
#COPY src ./src
#EXPOSE 9003
#CMD ["./mvnw", "spring-boot:run"]






#FROM continuumio/miniconda:4.5.4
#RUN pip install mlflow>=1.0
#    && pip install azure-storage-blob==12.3.0
#    && pip install numpy==1.14.3
#    && pip install scipy
#    && pip install pandas==0.22.0
#   && pip install scikit-learn==0.19.1
#   && pip install cloudpickle*/




