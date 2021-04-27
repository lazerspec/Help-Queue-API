FROM gradle:6.3.0 AS build-stage

COPY . /build
WORKDIR /build
RUN ls && gradle -v
RUN gradle build

FROM java:8 AS runtime
WORKDIR /opt/ticket-app
COPY --from=build-stage /build/libs/ticket-help-api-1.0-SNAPSHOT.jar ticket-api.jar

RUN ls

ENTRYPOINT ["/usr/bin/java","-jar","-Dspring.profiles.active=cloud","ticket-api.jar"]