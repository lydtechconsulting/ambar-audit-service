# Audit Service for Event Sourcing with Ambar Demo

This repository contains an audit service Spring Boot application for capturing cookery club membership status changes.

It is used in conjunction with the Ambar event sourcing demo in the repository:

https://github.com/lydtechconsulting/ambar-event-sourcing

## Application Overview

The application provides an endpoint to receive notifications of membership submissions and evaluations, and writes the associated status updates to a Postgres materialised view.  This database can then be queried via the application's REST API.

![Audit service](resources/ambar-audit-application.png)

## Running the Application 

Build the application (with Java 21), build its docker container, and start along with a Postgres database instance:

```
mvn clean install
docker build -t ambar-audit-service .
docker-compose up -d
```

## Demo

The demo adds this new service to an existing deployment that already has historic events stored in the event store.  It demonstrates that those events will be delivered to the new application when the Ambar emulator is restarted.

The steps to run the demo are in the `ambar-event-sourcing` project [Readme](https://github.com/lydtechconsulting/ambar-event-sourcing/blob/main/README.md) file.
