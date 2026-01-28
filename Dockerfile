# =========================
# Stage 1: Angular build
# =========================
FROM node:14-buster AS nodeclient

# 🔧 Fix Debian Buster EOL
RUN sed -i 's|deb.debian.org|archive.debian.org|g' /etc/apt/sources.list && \
    sed -i 's|security.debian.org|archive.debian.org|g' /etc/apt/sources.list && \
    echo 'Acquire::Check-Valid-Until "false";' > /etc/apt/apt.conf.d/99no-check-valid && \
    apt-get update && apt-get -y upgrade

RUN mkdir -p /tmp
WORKDIR /tmp/qcloud2-client
COPY qcloud2-client/ /tmp/qcloud2-client/
RUN npm install
RUN npm run transpile:prod

RUN apt-get clean


# =========================
# Stage 2: Spring Boot build
# =========================
FROM biocorecrg/debian-perlbrew-pyenv3-java:buster AS jarserver

# 🔧 Fix Debian Buster EOL
RUN sed -i 's|deb.debian.org|archive.debian.org|g' /etc/apt/sources.list && \
    sed -i 's|security.debian.org|archive.debian.org|g' /etc/apt/sources.list && \
    echo 'Acquire::Check-Valid-Until "false";' > /etc/apt/apt.conf.d/99no-check-valid && \
    apt-get update && apt-get -y upgrade

RUN mkdir -p /tmp
WORKDIR /tmp
COPY mvn* /tmp/
COPY pom.xml /tmp/
COPY src/ /tmp/src/
COPY --from=nodeclient /tmp/dist/ /tmp/src/main/resources/static/
RUN mvn package -DskipTests -f pom.xml

RUN apt-get clean


# =========================
# Stage 3: Runtime
# =========================
FROM biocorecrg/debian-perlbrew-pyenv3-java:buster

# 🔧 Fix Debian Buster EOL (necessari perquè instal·lem gettext-base)
RUN sed -i 's|deb.debian.org|archive.debian.org|g' /etc/apt/sources.list && \
    sed -i 's|security.debian.org|archive.debian.org|g' /etc/apt/sources.list && \
    echo 'Acquire::Check-Valid-Until "false";' > /etc/apt/apt.conf.d/99no-check-valid && \
    apt-get update && apt-get -y upgrade && \
    apt-get -y install gettext-base && \
    apt-get clean

ENV QCLOUD2_API_PREFIX=http://localhost:8089/

VOLUME /tmp
RUN mkdir -p /config
RUN mkdir -p /app
WORKDIR /app

COPY --from=jarserver /tmp/target/*jar /app/QCloud2.jar
COPY ./entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

ENTRYPOINT ["/app/entrypoint.sh"]

