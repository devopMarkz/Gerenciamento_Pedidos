FROM maven:3.9.9-amazoncorretto-17 AS builder

WORKDIR /build

COPY . .

RUN mvn clean package -DskipTests

FROM amazoncorretto:17

WORKDIR /app

COPY --from=builder /build/target/*.jar eureka-server.jar

ENTRYPOINT ["java", "-jar", "eureka-server.jar"]