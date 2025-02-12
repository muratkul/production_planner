FROM openjdk:17-jdk-slim

LABEL maintainer="muratkul"

COPY target/production_planner-1.0.jar production_planner-1.0.jar

ENTRYPOINT ["java", "-jar", "production_planner-1.0.jar"]