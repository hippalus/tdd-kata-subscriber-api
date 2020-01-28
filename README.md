# kata-subscriber-api


# Build & Create Image

./mvnw clean package

./mvnw dockerfile:build

# Run Container

docker run -p8080:8080  hippalus/subscriber-api