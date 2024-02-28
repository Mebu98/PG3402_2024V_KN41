# syntax=docker/dockerfile:1.4
FROM node:20.11.0-alpine

RUN mkdir /project
WORKDIR /project

COPY . .

RUN npm install
CMD ["npm", "run", "host"]