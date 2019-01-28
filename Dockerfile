FROM openjdk:8-jdk-alpine

COPY reporting/target /app/target
WORKDIR /app/target

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "reporting-core-0.0.1-SNAPSHOT.jar"]

#COPY reporting-gateway/target /app/target
#WORKDIR /app/target
#
#EXPOSE 8080
#
#ENTRYPOINT ["java", "-jar", "reporting-gateway-0.0.1-SNAPSHOT.jar"]

