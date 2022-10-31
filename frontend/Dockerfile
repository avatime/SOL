FROM node:16.16.0 as build-stage
WORKDIR /var/jenkins_home/workspace/frontend/front
COPY package*.json ./
RUN npm i -y
COPY . .
RUN npm run build
CMD [ "npm", "run", "dev"]