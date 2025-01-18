# Audit Service for Event Sourcing with Ambar Demo

An audit service for cookery club membership status changes.  The latest status for each applicant is captured.

Used in conjunction with the Ambar event sourcing demo in the following repo:
https://github.com/lydtechconsulting/ambar-event-sourcing

This demo adds a new service to an existing deployment that already has historic events stored in the event store.  It demonstrates that those events will be delivered to the new application when the Ambar emulator is restarted.

## Application Overview

The application provides an endpoint to receive notifications of membership submissions and evaluations, and writes the associated status updates to a Postgres materialised view.  This database can then be queried via the REST API.

![Audit service](resources/ambar-audit-application.png)

### Running The Demo

1. Build this Spring Boot application with Java 21, and dockerise.

```
mvn clean install
docker build -t ambar-audit-service .
```

2. In the [ambar-event-sourcing project](https://github.com/lydtechconsulting/ambar-event-sourcing/blob/main/local-development/ambar-config.yaml#L48), ensure that the Ambar configuration for the audit endpoint `Audit_CookingClub_Membership` is commented out in the [ambar-config.yaml](https://github.com/lydtechconsulting/ambar-event-sourcing/blob/main/local-development/ambar-config.yaml#L48) file.

3. Start the Ambar docker containers and run the demo from the [ambar-event-sourcing](https://github.com/lydtechconsulting/ambar-event-sourcing) project to populate the event store with events (as described in that Readme).

4. Start this Spring Boot application `ambar-audit-service` and the Postgres database in docker containers:
```
docker-compose up -d
```

5. In the [ambar-event-sourcing project](https://github.com/lydtechconsulting/ambar-event-sourcing/blob/main/local-development/ambar-config.yaml#L48), uncomment the Ambar configuration for the audit endpoint `Audit_CookingClub_Membership` in the [ambar-config.yaml](https://github.com/lydtechconsulting/ambar-event-sourcing/blob/main/local-development/ambar-config.yaml#L48) file.

6. Restart the Ambar emulator.  The historic events should be sent to the audit service projection endpoint, and written to the Postgres materialised view.
```
docker restart event-sourcing-event-bus
```

7. Query the materialised view via the REST API to view the latest membership statuses.
```
curl http://localhost:8099/api/v1/audit/cooking-club/membership/query/list
```
Observe the output:
```
[{"name":"Linda Thomas","status":"Approved","createdAt":"2025-01-18T16:47:50.343001Z","lastUpdatedAt":"2025-01-18T16:47:50.395517Z"},{"name":"Patricia Jones","status":"Rejected","createdAt":"2025-01-18T16:47:51.761195Z","lastUpdatedAt":"2025-01-18T16:47:51.810726Z"} ......... ]
```
