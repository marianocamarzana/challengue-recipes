FROM openjdk:11-jre-slim
WORKDIR /src
COPY target/recipes-0.0.1-SNAPSHOT.jar /src/recipes-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "recipes-0.0.1-SNAPSHOT.jar"]