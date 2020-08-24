# library-stats

This microservice manages statistics in the library.

### Running the application

*Please note that this application requires library-shelf, so the commands below will start it too.*

1. Build the docker images

    ```
    ./mvnw -Pprod verify jib:dockerBuild
    ```

2. Run docker compose

    ```
    docker-compose -f src/main/docker/app.yml up -d
    ```
   
3. Access of application
    - jhipster registry (eureka, configserver, springbootadmin, swagger UI): localhost:8761   (admin / admin)
