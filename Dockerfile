FROM openjdk:17-jdk-alpine
MAINTAINER galo_ortega
COPY target/ClientBP-0.0.1-SNAPSHOT.jar ClientBP-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/ClientBP-0.0.1-SNAPSHOT.jar"]