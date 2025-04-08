# Etapa de compilación
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
