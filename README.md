# library-stats

This microservice manages statistics in the library.

### Running the application
 
 1. Build the docker images
 
     ```
     ./mvnw -Pprod verify jib:dockerBuild
     ```
 
 2. Run docker compose
     
     See `library-opts` repository for further details!
