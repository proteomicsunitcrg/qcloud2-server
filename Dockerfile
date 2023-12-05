FROM node:14-buster as nodeclient

RUN apt update && apt upgrade -y
RUN mkdir -p /tmp
WORKDIR /tmp
COPY qcloud2-client/ /tmp/
RUN npm install
RUN npm run transpile:prod
# Result in dist/
RUN apt clean

FROM biocorecrg/debian-perlbrew-pyenv3-java:buster as jarserver

RUN apt update && apt upgrade -y
RUN mkdir -p /tmp
WORKDIR /tmp
COPY mvn* /tmp/
COPY pom.xml /tmp/
COPY src/ /tmp/src/
COPY --from=nodeclient /tmp/dist/ /tmp/src/main/resources/static/
RUN mvn package -DskipTests -f pom.xml
# Result in target/*jar
RUN apt clean

FROM biocorecrg/debian-perlbrew-pyenv3-java:buster
RUN apt update && apt upgrade -y && apt -y install gettext-base
ENV QCLOUD2_API_PREFIX=http://localhost:8089/
VOLUME /tmp
RUN mkdir -p /config
RUN mkdir -p /app
WORKDIR /app
COPY --from=jarserver /tmp/target/*jar /app
# We don't keep version number for making things easier
COPY ./entrypoint.sh /app/
RUN chmod +x /app/entrypoint.sh
RUN mv /app/QCloud2-*.jar /app/QCloud2.jar 
# ENTRYPOINT java -jar -Dspring.config.location=file:/config/application.yml -Dspring.profiles.active=prod /app/QCloud2.jar
ENTRYPOINT /app/entrypoint.sh
