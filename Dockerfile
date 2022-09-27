FROM --platform=linux/amd64 openjdk:17-alpine3.14 as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests

FROM --platform=linux/amd64 openjdk:17-alpine3.14
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build /workspace/app/target/demo-spring-boot-on-kubernetes-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar", "app.jar"]