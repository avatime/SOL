FROM node:16.16.0 as build-stage
WORKDIR /var/jenkins_home/workspace/BuildFrontend2/frontend2
COPY package*.json ./
RUN npm i -y
COPY . .

EXPOSE 3000
CMD [ "npm", "-s", "build"]