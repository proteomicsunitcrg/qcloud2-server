FROM biocorecrg/debian-perlbrew-pyenv3-java:buster

# 🔧 Fix Debian Buster EOL
RUN sed -i 's|deb.debian.org|archive.debian.org|g' /etc/apt/sources.list && \
    sed -i 's|security.debian.org|archive.debian.org|g' /etc/apt/sources.list && \
    echo 'Acquire::Check-Valid-Until "false";' > /etc/apt/apt.conf.d/99no-check-valid && \
    apt-get update && apt-get -y upgrade && \
    apt-get clean

RUN mkdir -p /app /config /tmp
WORKDIR /app

COPY target/QCloud2-*.jar /app/QCloud2.jar

EXPOSE 8089

ENTRYPOINT ["java","-jar","-Dspring.config.location=file:/config/application.yml","/app/QCloud2.jar"]

