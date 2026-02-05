############################################
# 1️⃣ BUILD STAGE
############################################
FROM maven:3.9.9-amazoncorretto-21 AS builder

WORKDIR /build

# Copy only pom.xml first (dependency caching)
COPY pom.xml .

# Pre-download dependencies
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -q -e -DskipTests dependency:go-offline

# Copy source AFTER dependencies
COPY src ./src

# Build
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -DskipTests clean package


############################################
# 2️⃣ RUNTIME STAGE
############################################
FROM amazoncorretto:21-alpine

RUN addgroup -S spring && adduser -S spring -G spring

WORKDIR /app

COPY --from=builder /build/target/*.jar app.jar

RUN chown -R spring:spring /app
USER spring

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]
