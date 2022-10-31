FROM node:16.15.0 as build-stage
WORKDIR /var/jenkins_home/workspace/deployFrontend/frontend
COPY package*.json ./
RUN npm i -y
COPY . .
CMD [ "npm", "run", "build"]