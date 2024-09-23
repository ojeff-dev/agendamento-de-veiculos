#Build
FROM maven:3-openjdk-17 AS build-image

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

#Execução
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY --from=build-image /app/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]