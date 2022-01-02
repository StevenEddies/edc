# syntax=docker/dockerfile:1

FROM nginx:1.21.5

COPY app /usr/share/nginx/html

COPY ng.conf /etc/nginx/conf.d/ng.conf
RUN rm /etc/nginx/conf.d/default.conf

EXPOSE 80/tcp
