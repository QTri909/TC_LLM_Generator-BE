# syntax=docker/dockerfile:1.4
# ============================================
# OPTIMIZED Multi-stage build for Spring Boot
# Uses BuildKit cache mounts for FAST rebuilds
# ============================================

# Stage 1: Build stage with cached Maven dependencies
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

# Copy only pom.xml first for dependency caching
COPY pom.xml .

# Download dependencies with BuildKit cache mount
# This caches ~/.m2 between builds - HUGE speed improvement
RUN --mount=type=cache,target=/root/.m2 \
    mvn dependency:go-offline -B -q

# Copy source code
COPY src ./src

# Build with cached .m2 and parallel threads
RUN --mount=type=cache,target=/root/.m2 \
    mvn clean package -DskipTests -B -q -T 1C

# Stage 2: Minimal runtime image
FROM eclipse-temurin:21-jre-alpine

LABEL maintainer="TC_LLM_Generator Team"

WORKDIR /app

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring

# Copy JAR from build stage
COPY --from=build /app/target/*.jar app.jar

RUN chown spring:spring app.jar
USER spring:spring

EXPOSE 8080

# Optimized JVM options for container
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+UseG1GC -XX:+UseStringDeduplication"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
