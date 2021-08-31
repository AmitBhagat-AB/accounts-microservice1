FROM openjdk:8-jdk-alpine as builder
#COPY the dependency
WORKDIR /app
COPY mvnw .
COPY pom.xml .
COPY .mvn .mvn
RUN chmod +x ./mvnw
RUN ./mvnw -B dependency:go-offline

# Build phaseCompile the source code
COPY src src
RUN .\mvnw clean package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Package phase
FROM openjdk:8-jre-alpine as stage
ARG DEPENDENCY=/app/target/dependency

# Copy the dependency application file from builder stage artifact
COPY --from=builder ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=builder ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=builder ${DEPENDENCY}/BOOT-INF/classes /app
EXPOSE 8222
ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.classpath.accountsapi.AccountsApiApplication"]