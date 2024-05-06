#CMD bash apt-get update && apt-get install -y postgres:13.2-alpine
#ARG dockerfile=postgres_init/Dockerfile
#WORKDIR /opt/app
#COPY ${dockerfile} Dockerfile
##RUN docker build -t test-image .

FROM openjdk:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=build/libs/balancing-0.0.1-SNAPSHOT.jar
#EXPOSE 8080
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

#FROM ibm-semeru-runtimes:open-17-jre-focal
#MAINTAINER https://github.com/krajoff/spring-balancing
#
#ADD ${JAR_FILE} app.jar
#ENV _JAVA_OPTIONS="-XX:MaxRAM=100m"

#CMD java $_JAVA_OPTIONS -Dspring.profiles.active=test -Dspring.datasource.url=balancing-postgres.internal -Dspring.liquibase.url=$SPRING_LIQUIBASE_URL -Dspring.datasource.username=$SPRING_DATASOURCE_USERNAME -Dspring.datasource.password=$SPRING_DATASOURCE_PASSWORD -jar baby-0.0.1-SNAPSHOT.jar
#ARG DOCKER_FILE=postgres_init/Dockerfile
#ADD ${DOCKER_FILE} Dockerfile
##CMD bash sudo apt-get update && apt-get install -y postgres:13.2-alpine
#RUN apk add docker
#RUN set -ex && apk --no-cache add sudo
#CMD bash sudo docker build -t postgresdb .
#CMD bash sudo docker run --name postgresdb -p 5432:5432 -d postgresdb
#ENTRYPOINT ["java","-jar","/app.jar"]