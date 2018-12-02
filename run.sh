#!/usr/bin/env sh

cd reportingapi/
./mvnw install
cd ../reporting
nohup ./mvnw spring-boot:run &
cd ../reporting-gateway
./mvnw spring-boot:run
