# QCloud2 Server

Backend REST API for QCloud, a quality control platform for mass spectrometry instruments.

## Tech stack

- Java 17 / Spring Boot
- Maven
- MySQL
- Docker

## Getting started (development)

Requirements: Java 17, Maven, MySQL (or use the provided Docker setup).

```
# Adapt application.yml or docker-compose.dev.yml as needed
docker compose -f docker-compose.dev.yml build
docker compose -f docker-compose.dev.yml up -d
```

See [qcloud2-client](https://github.com/proteomicsunitcrg/qcloud2-client) for the front-end counterpart.

## References

- [Spring Boot documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html)

---

Maintained by the CRG Proteomics Unit.
