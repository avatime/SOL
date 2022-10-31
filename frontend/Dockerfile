FROM node:16.15.0 as build-stage
WORKDIR /var/jenkins_home/workspace/deployFrontend/frontend
COPY package*.json ./
RUN npm i -y
COPY . .
RUN npm run build
FROM nginx:stable-alpine as production-stage

EXPOSE 80
CMD ["nginx", "-g","daemon off;"]