FROM ubuntu:18.04
RUN apt-get update && apt-get install lib32stdc++6


FROM maven:amazoncorretto as build
COPY src .
COPY pom.xml .
CMD ["mvn", "clean", "package"]
RUN apt-get update && apt-get install lib32stdc++6
#RUN mvn -f pom.xml clean package/install
#mvn spring-boot:run


#3.9.6-alpine
FROM python:3.7.0
RUN pip install mlflow==1.20.1
RUN pip install azure-storage-blob
#CMD ["pip" , "install" , "psycopg2"]
#CMD ["mlfow", "db" , "upgrade" , "postgresql://emerge:emerge0000@analysedatabase.clzpxhj7ijqm.eu-west-2.rds.amazonaws.com:5432/analyseDatabase"]
ENTRYPOINT ["mlflow", "server"]
# ,"--host" , "0.0.0.0", "--backend-store-uri", "postgresql://emerge:emerge0000@analyzedatabase.clzpxhj7ijqm.eu-west-2.rds.amazonaws.com:5432/analyzeDatabase", "--default-artifact-root", "DefaultEndpointsProtocol=https;AccountName=emergeartifactstore;AccountKey=8atYYzEevj5iElQfwWeyEnshETGfftBS75gLXF2O7Ay7HV2DEeXLic0tjeswqGYjPVyXLAIXQzohVMEBtRz9ew==;EndpointSuffix=core.windows.net"]
#CMD ["mlflow", "ui"]


#FROM amazonlinux:2
#RUN amazon-linux-extras enable corretto11 && yum install -y java-11.0.12-amazon-corretto && yum clean all
#1.8.0
#11-alpine-jdk
FROM amazoncorretto:11
#FROM openjdk:11
#ARG JAR_FILE=target/*.jar
#COPY --from=build ${JAR_FILE} Analyse_Service.jar
#RUN apt-get install lib32stdc++6
COPY target/classes/TData.CSV TData.CSV
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