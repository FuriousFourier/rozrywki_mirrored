#!/usr/bin/env sh

cd reportingapi/
./mvnw install
echo RERPORTINGAPI INSTALLED
cd ../mof-ftp-mock
nohup ./mvnw clean -U compile exec:java &
cd ../reporting
./mvnw spring-boot:run
