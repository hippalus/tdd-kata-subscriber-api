# kata-subscriber-api
[![Build Status](https://travis-ci.com/hippalus/tdd-kata-subscriber-api.svg?branch=master)](https://travis-ci.com/hippalus/tdd-kata-subscriber-api)
[![codecov](https://codecov.io/gh/hippalus/tdd-kata-subscriber-api/branch/master/graph/badge.svg)](https://codecov.io/gh/hippalus/tdd-kata-subscriber-api)

# Build & Create Image

./mvnw clean package

./mvnw dockerfile:build

# Run Container

docker run -p8080:8080  hippalus/subscriber-api