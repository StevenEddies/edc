# syntax=docker/dockerfile:1

FROM openjdk:17.0

COPY . /opt/edc-backend

VOLUME /opt/edc-backend/var

WORKDIR /opt/edc-backend/bin

EXPOSE 8080/tcp

CMD ["./edc-backend", "server", "../var/config.yaml"]
