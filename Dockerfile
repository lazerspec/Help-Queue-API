FROM gradle:6.3.0 AS build-stage
COPY . /build
RUN ls
WORKDIR /build
RUN ls
RUN gradle build -x test
RUN ls build/

FROM java:8 AS runtime
RUN ls
WORKDIR /opt/ticket-app
RUN ls
COPY --from=build-stage /build/libs/ticket-help-api-1.0-SNAPSHOT.jar ticket-api.jar

RUN ls

ENTRYPOINT ["/usr/bin/java","-jar","-Dspring.profiles.active=cloud","ticket-api.jar"]