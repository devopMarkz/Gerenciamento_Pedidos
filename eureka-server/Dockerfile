FROM maven:3.9.9-amazoncorretto-21-debian AS builder
WORKDIR /builder
COPY . .
RUN mvn package -DskipTests

FROM amazoncorretto:21
WORKDIR /app
COPY --from=builder ./builder/target/*.jar ./eureka-server.jar
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]