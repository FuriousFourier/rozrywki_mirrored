#!/usr/bin/env sh

cd reportingapi/
./mvnw install
echo RERPORTINGAPI INSTALLED
cd ../reporting
nohup ./mvnw spring-boot:run &
echo REPORTING IS BEING RUN
cd ../mof-ftp-mock
nohup mvn clean -U compile exec:java &
echo MOF FTP SERVER STARTING
cd ../reporting-gateway
./mvnw spring-boot:run
