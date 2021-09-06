FROM node:latest
WORKDIR /app
COPY package.json ./
RUN yarn install
COPY . .
CMD ["npm", "start"]