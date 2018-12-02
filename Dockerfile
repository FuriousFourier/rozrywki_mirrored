FROM openjdk:8

ADD reporting /app/reporting
ADD reportingapi /app/reportingapi
ADD reporting-gateway /app/reporting-gateway
ADD run.sh /app
WORKDIR /app

EXPOSE 8080
EXPOSE 8081

CMD ["./run.sh"]
