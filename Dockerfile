# Docker multi-stage build

# 1. Building the App with Maven
FROM maven:3-jdk-11

ADD . /carpark
WORKDIR /carpark

# Just echo so we can see, if everything is there :)
RUN ls -l

# Run Maven build
RUN mvn clean install


# 2. Just using the build artifact and then removing the build-container
FROM openjdk:11-jdk

VOLUME /tmp

# Add Spring Boot app.jar to Container
COPY --from=0 "/carpark/backend/target/backend-0.0.1-SNAPSHOT.jar" app.jar

ENV JAVA_OPTS=""

# Fire up our Spring Boot app by default
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]