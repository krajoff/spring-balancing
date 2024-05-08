FROM openjdk:17-jdk-alpine
MAINTAINER https://github.com/krajoff/spring-balancing
ARG JAR_FILE=build/libs/balancing-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]