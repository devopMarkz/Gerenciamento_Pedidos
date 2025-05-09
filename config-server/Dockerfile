FROM maven:3.9.9-amazoncorretto-17-debian AS builder
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM amazoncorretto:17
WORKDIR /app
COPY --from=builder /build/target/*.jar config-server.app
ENTRYPOINT ["java", "-jar", "config-server.app"]