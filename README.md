**Help Queue API**

This spring-boot application serves as the API to the help queue UI. Its purpose is to manage departments and their tickets in a queue awaiting for someone 
to pickup the ticket to be worked on. There are three states which a ticket can be: open, in progress and closed. Tickets can belong to departments though this is 
not mandatory. As well as managing tickets, departments can also be managed.

The required setup files such as the schema.sql and data.sql are located in the resources folder as a H2 database is used for local deployment. The API can also be operated
via Swagger. It's an API documentation tool and when the API is run, can be found at this endpoint: 

`/swagger-ui.html`

Additionally, the application has both information and health endpoints:

`/manage/info`
`/manage/health`


**Pre-requisites**

Gradle 6.3+

Java 8

**Run Instructions**

This can be run either from the command line or via an IDE. To run via command line please build the jar file:

`gradle clean build`

At the root of the project a build/libs directory will be created and in that
a jar `ticket-help-api-1.0-SNAPSHOT.jar`.


To run the jar use the command:
`java -jar build/libs/ticket-help-api-1.0-SNAPSHOT.jar`

To run in an IDE, simply import the project or open it in one.

**Project Jira Board**

A look at user stories, tasks, story points etc used can be found on the below link:

https://parvir-chomber.atlassian.net/secure/RapidBoard.jspa?rapidView=2&projectKey=HQ


**Tests**

Testing uses a H2 in-mem database populated with test data which almost mirrors the production database