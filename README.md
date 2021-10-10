# effective-fiesta

This repository contains the sample implementation for the multi-tenant spring-boot application with a shared database approach. The HashiCorp vault rotates the database credentials for improving database security.

## Information

- Based on [Spring Boot](https://spring.io/projects/spring-boot)
- Based on [HashiCorp Vault](https://www.vaultproject.io/)
- Requires [Java 8+](https://openjdk.java.net/install/), [Docker](https://www.docker.com/), [Docker-Compose](https://docs.docker.com/compose/install/), [Apache Maven](https://maven.apache.org/download.cgi)

## Usage

Use the following command to start the services required by the spring-boot application.

```bash
$ docker-compose up -d
```

Check the status of the services with the following command. Wait till all containers are in a healthy state.

```bash
$ docker-compose ps
```

Use the following command to run the spring-boot application.

```bash
$ ./mvnw spring-boot:run
```

To stop the docker-compose deployment, use the following command.

```bash
$ docker-compose down -v
```
