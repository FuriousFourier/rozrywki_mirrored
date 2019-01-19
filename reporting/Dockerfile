FROM openjdk:8

ADD reporting /app/reporting
ADD reportingapi /app/reportingapi
ADD mof-ftp-mock /app/mof/mof-ftp-mock
ADD reporting/run.sh /app
WORKDIR /app

EXPOSE 8080

CMD ["./run.sh"]
