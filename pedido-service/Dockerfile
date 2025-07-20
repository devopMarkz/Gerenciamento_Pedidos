FROM maven:3.9.9-amazoncorretto-21-debian AS builder
WORKDIR /builder
COPY . .
RUN mvn package -DskipTests

FROM amazoncorretto:21
WORKDIR /app
COPY --FROM=builder ./builder/target/*.jar ./pedido-service.jar
ENTRYPOINT ["java", "-jar", "pedido-service.jar"]