# 1. Build and package the enquiry service
mvn clean install

# 2. Build the docker container with the service
docker build -t ambar-enquiry-service .

# 3. Stop the docker containers if they are running.
docker compose down

# 4. Remove the Postgres volume if it exists.
docker volume rm ambar-enquiry-service_postgres-enquiry

# 5. Start the Postgres database (for the materialised view) and the enquiry service
docker compose up -d
