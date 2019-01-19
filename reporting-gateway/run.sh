#!/usr/bin/env sh

cd reportingapi/
./mvnw install
echo RERPORTINGAPI INSTALLED
cd ../reporting-gateway
./mvnw spring-boot:run
