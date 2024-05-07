# Use a base image with Java
FROM openjdk:17-jdk-alpine
MAINTAINER https://github.com/krajoff/spring-balancing
#VOLUME /tmp
ARG JAR_FILE=build/libs/balancing-0.0.1-SNAPSHOT.jar
#ARG DOCKER_FILE=postgres_init/Dockerfile
#ARG SPRING_DATASOURCE_HOSTNAME=balancing-postgres.internal
#ARG SPRING_DATASOURCE_URL=balancing-postgres.flycast
#ARG SPRING_DATASOURCE_USERNAME=postgres
#ARG SPRING_DATASOURCE_PASSWORD=H8AGO842ZZElQXq
# Copy the built jar file into the image
COPY ${JAR_FILE} app.jar
# Set the entry point to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]

#ENV _JAVA_OPTIONS="-XX:MaxRAM=100m"
#CMD java $_JAVA_OPTIONS -Dspring.datasource.url=${SPRING_DATASOURCE_URL} \
#-Dspring.datasource.username=${SPRING_DATASOURCE_USERNAME} \
#-Dspring.datasource.password=${SPRING_DATASOURCE_PASSWORD} \
#-jar balancing-0.0.1-SNAPSHOT.jar