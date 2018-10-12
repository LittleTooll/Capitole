FROM openjdk:8-jdk-alpine
COPY target/challenge-0.1.0.jar /usr/src/challenge/
WORKDIR /usr/src/challenge
ENTRYPOINT exec java -Djava.security.egd=file:/dev/./urandom -Dserver.port=$SERVER_PORT -Dspring.data.mongodb.uri=$MONGODB_URI -jar challenge-0.1.0.jar
