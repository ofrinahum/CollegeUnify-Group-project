FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/project-0.0.1-SNAPSHOT.jar project.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "project.jar"]