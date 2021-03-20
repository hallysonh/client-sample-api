# Use same version of gradle in gradle/gradle-wrapper.properties
FROM gradle:6.8.3-jdk11-openj9 as build
WORKDIR /home/gradle/src
# Only copy gradle files
COPY build.gradle settings.gradle ./
# Only download dependencies
RUN gradle clean build --no-daemon > /dev/null 2>&1 || true
# Copy all files
COPY --chown=gradle:gradle . ./
# Build jar
RUN gradle bootJar --no-daemon -i --stacktrace

FROM openjdk:11-jre-slim
ENV apiKey ""
EXPOSE 3000
WORKDIR /usr/src/java-app
# Copy jar from build stage
COPY --from=build /home/gradle/src/build/libs/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]