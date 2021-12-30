# syntax=docker/dockerfile:1

FROM openjdk:14.0.2

COPY . /opt/crystal-run-backend

VOLUME /opt/crystal-run-backend/var

WORKDIR /opt/crystal-run-backend/bin

EXPOSE 8080/tcp

CMD ["./crystal-run-backend", "server", "../var/config.yaml"]
