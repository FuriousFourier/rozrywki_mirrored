FROM openjdk:8

ADD reporting-gateway /app/reporting-gateway
ADD reportingapi /app/reportingapi
ADD reporting-gateway/run.sh /app
WORKDIR /app

EXPOSE 8081

RUN cd reportingapi/ && ./mvnw install && cd ../reporting ./mvnw install

CMD ["./run.sh"]
