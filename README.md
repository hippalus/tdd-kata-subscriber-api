# kata-subscriber-api
[![Build Status](https://travis-ci.com/hippalus/tdd-kata-subscriber-api.svg?branch=master)](https://travis-ci.com/hippalus/tdd-kata-subscriber-api)
[![codecov](https://codecov.io/gh/hippalus/tdd-kata-subscriber-api/branch/master/graph/badge.svg)](https://codecov.io/gh/hippalus/tdd-kata-subscriber-api)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.subscriber%3Atdd-kata-subscriber-api&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.subscriber%3Atdd-kata-subscriber-api)

# Build & Create Image

./mvnw clean package

./mvnw dockerfile:build

# Run Container

docker run -p8080:8080  hippalus/subscriber-api