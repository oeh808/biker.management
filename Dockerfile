FROM eclipse-temurin:21-jdk-alpine

EXPOSE 8080

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -Pstaging

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]