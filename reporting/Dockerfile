FROM openjdk:8

ADD reporting /app/reporting
ADD reportingapi /app/reportingapi
ADD reporting/run.sh /app
WORKDIR /app

EXPOSE 8080

RUN cd reportingapi/ && ./mvnw install && cd ../reporting ./mvnw install

ENTRYPOINT ["./run.sh"]
