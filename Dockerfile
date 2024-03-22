FROM openjdk:17-jdk-slim
COPY target/Learning_java_springboot_minimal_with_database-0.0.1-SNAPSHOT-spring-boot.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]