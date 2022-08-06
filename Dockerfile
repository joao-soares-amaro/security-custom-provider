FROM maven:3.8.1-jdk-11-openj9 AS builder

WORKDIR /app

COPY src src
COPY pom.xml pom.xml

RUN mvn clean package -DskipTests

FROM adoptopenjdk/openjdk11-openj9:alpine-jre

COPY datadog/dd-java-agent.jar dd-java-agent.jar
COPY --from=builder /app/target/security-custom-provider*.jar security-custom-provider.jar

EXPOSE 8080

CMD ["java","-javaagent:dd-java-agent.jar","-jar","security-custom-provider.jar"]