#FROM node:14-alpine AS development
FROM node:latest-alpine AS development
ENV NODE_ENV development
# Add a work directory
WORKDIR /app
# Cache and Install dependencies
COPY package.json .
COPY yarn.lock .
RUN yarn install
# Copy app files
COPY . .
# Expose port
EXPOSE 3000
# Start the app
CMD [ "yarn", "start" ]

########################
#FROM node:latest
#WORKDIR /src
#COPY package.json ./
#RUN yarn install
#COPY . .
#CMD ["npm", "start"]

########################
#FROM node:12.2.0-alpine
#ENV PATH /app/node_modules/.bin:$PATH
#COPY package.json /app/package.json
#RUN npm install
#RUN npm install react-scripts@3.0.1 -g
#CMD ["npm", "start"]
#CMD ["npm", "start-electron"]