FROM java:8

CMD echo "Creating ./data folder..."
RUN mkdir -p ~/data
ADD /data /data

CMD echo "Creating data.json dile..."
RUN  touch /data/data.json

ARG JAR_FILE

ADD  target/${JAR_FILE} target/tdd-kata-subscriber-api-1.0.jar

EXPOSE 8080

ENV JAVA_OPTS=""

ENTRYPOINT java $JAVA_OPTS -jar target/tdd-kata-subscriber-api-1.0.jar