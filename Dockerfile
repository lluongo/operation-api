# syntax=docker/dockerfile:experimental

FROM maven:3.6.3-jdk-11 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN --mount=type=cache,target=/root/.m2/repository mvn clean package -DskipTests

FROM openjdk:11-jre
EXPOSE 8080
WORKDIR /app
CMD exec java $JAVA_OPTS -jar operation-api.jar
COPY --from=build /build/target/operation-api*.jar /app/operation-api.jar
