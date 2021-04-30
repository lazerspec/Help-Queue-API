FROM gradle:6.3.0 AS build-stage
COPY . /build
WORKDIR /build
RUN gradle build
RUN ls build/libs

FROM java:8 AS runtime
WORKDIR /opt/ticket-app
COPY --from=build-stage /build/build/libs/ticket-help-api-1.0-SNAPSHOT.jar ticket-api.jar
ENTRYPOINT ["/usr/bin/java","-jar","-Dspring.profiles.active=cloud","ticket-api.jar"]