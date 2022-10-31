FROM node:16.15.0 as build-stage
WORKDIR /var/jenkins_home/workspace/deployFront/frontend
COPY package*.json ./
RUN npm i -y
COPY . .
RUN npm run build
CMD [ "npm", "run", "start"]
