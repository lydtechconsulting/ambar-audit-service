# Simple Ambar destination
A simple Spring Boot service that has some REST endpoints which can be registered as 'destinations' in ambar, and therefore receive events.

Currently the event is just logged and will eventually be used to CRUD an aggregate in Postgres

### Building
`./mvnw clean install`
`docker build -t lydtech-ambar-example .`

### Running locally (to test)
Either 
- run `ExampleAmbarDestinationApplication` in IntelliJ
- run ` ./mvnw spring-boot:run` to run via Maven
- run `docker run -d  -p 8099:8099 lydtech-ambar-example` to run via Docker

### testing locally

```
curl -v -d '{"event": "myValue"}' http://localhost:8099/api/submit
```

observe a HTTP 200 response such as
```
{"result":{"success":{}}}
```
