# -------- Stage 1: Build --------
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /build
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# -------- Stage 2: Runtime --------
FROM biocorecrg/debian-perlbrew-pyenv3-java:buster

RUN sed -i 's|deb.debian.org|archive.debian.org|g' /etc/apt/sources.list && \
    sed -i 's|security.debian.org|archive.debian.org|g' /etc/apt/sources.list && \
    echo 'Acquire::Check-Valid-Until "false";' > /etc/apt/apt.conf.d/99no-check-valid && \
    apt-get update && apt-get -y upgrade && \
    apt-get clean

RUN mkdir -p /app /config /tmp
WORKDIR /app

COPY --from=builder /build/target/*.jar /app/QCloud2.jar

EXPOSE 8088

ENTRYPOINT ["java","-jar","-Dspring.config.location=file:/config/application.yml","/app/QCloud2.jar"]

