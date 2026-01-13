# Build-time argument pointing to your built WAR (default: target/app.war)
ARG WAR_FILE=target/app.war

# Use a Java 17 runtime image suitable for Spring Boot apps
FROM maven:3.9-eclipse-temurin-17 AS build

# Create app directory
WORKDIR /app

# Re-declare the build arg so COPY can see it
ARG WAR_FILE
# Copy the built Spring Boot executable WAR into the image
COPY ${WAR_FILE} /app/app.war

# Expose the default Spring Boot port
EXPOSE 8080

# Allow users to pass JVM options at runtime (e.g. -Xmx)
ENV JAVA_OPTS=""

# Run the Spring Boot executable WAR. Use exec + sh -c so JAVA_OPTS expands and signals are forwarded.
ENTRYPOINT ["sh", "-c", "exec java ${JAVA_OPTS} -jar /app/app.war"]