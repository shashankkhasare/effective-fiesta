# effective-fiesta

This repository contains the sample implementation for the multi-tenant spring-boot application. The code uses the discriminator column with DB row-level security to achieve isolation of tenants on the shared database. The HashiCorp vault has the feature to rotate the database credentials for improving database security. The application uses the spring vault to fetch the DB credentials periodically from the HashiCorp vault. The spring-boot application uses PostgreSQL as a database.

## Information

- Based on [Spring Boot](https://spring.io/projects/spring-boot)
- Based on [HashiCorp Vault](https://www.vaultproject.io/)
- Requires [Java 8+](https://openjdk.java.net/install/), [Docker](https://www.docker.com/), [Docker-Compose](https://docs.docker.com/compose/install/), [Apache Maven](https://maven.apache.org/download.cgi), [JQ](https://stedolan.github.io/jq/download/)

## Usage

1. Use the following command to start the services required by the spring-boot application.

   ```bash
   $ docker-compose up -d
   ```

2. Check the status of the services with the following command. Wait till all containers are in a healthy state.

   ```bash
   $ docker-compose ps
   ```

3. Unseal the vault using the following command.

   ```bash
   $ sh unseal-vault-enable-approle-databases.sh
   ```

4. Export the root token for the vault.

   ```bash
   $ export VAULT_ROOT_TOKEN=...
   ```

5. Run the following script.

   ```bash
   $ sh setup-spring-vault-approle-postgresql.sh
   ```

6. Run the DB script to create the tables and policies.

   ```bash
   $ psql -U spring -h localhost -p 7358 -d springvault -f db_script.sql
   ```

7. Use the following command to run the spring-boot application.

   ```bash
   $ ./mvnw spring-boot:run
   ```

8. Add a student to the database for tenant1.

   ```bash
   $ curl 'http://localhost:8080/api/students' \
   --header 'X-TenantID: tenant1' \
   --header 'Content-Type: application/json' \
   --data-raw '{ "firstName" : "John", "lastName": "Doe"}'
   ```

9. Fetch all students of tenant1.

   ```bash
   $ curl --location --request GET 'http://localhost:8080/api/students' \
   --header 'X-TenantID: tenant1'
   ```

10. Fetch all students of tenant2.

    ```bash
    $ curl --location --request GET 'http://localhost:8080/api/students' \
    --header 'X-TenantID: tenant2'
    ```

11. To stop the docker-compose deployment, use the following command.
    ```bash
    $ docker-compose down -v
    ```

## References

1. https://callistaenterprise.se/blogg/teknik/2020/10/24/multi-tenancy-with-spring-boot-part6/
2. https://github.com/ivangfr/springboot-vault-examples
3. https://www.citusdata.com/blog/2018/02/13/using-hibernate-and-spring-to-build-multitenant-java-apps/
