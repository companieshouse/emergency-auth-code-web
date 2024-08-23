# Companies House Emergency Auth Code Web Service
The Companies House Web Service for requesting auth codes. This application is written using the [Spring Boot](http://projects.spring.io/spring-boot/) Java framework.

- Retrieves company information after using company lookup service.
- Displays selectable officers that can be chosen to receive an emergency auth code.

### Requirements
In order to run this Web app locally you will need to install:

- [Java 21](https://www.oracle.com/java/technologies/downloads/#java21)
- [Maven](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads)
- [Oracle query API](https://github.com/companieshouse/oracle-query-api)
- [Emergency auth code API](https://github.com/companieshouse/emergency-auth-code-api)

### Getting Started

1. [Configure your service](#configuration) if you want to override any of the defaults.
1. Run `make`
1. Run `./start.sh`


### Configuration

Key                | Description
-------------------|------------------------------------
`EMERGENCY_AUTH_CODE_WEB_PORT` |The port of the emergency auth code web service
`HUMAN_LOG`                    |For human readable logs

### Endpoints

| Method | Path                                                                  | Description                                                 |
|--------|-----------------------------------------------------------------------|-------------------------------------------------------------|
| GET    | `/auth-code-requests/start`                                           | Request an authentication code to be sent to a home address |
| POST   | `/auth-code-requests/start`                                           | Handle starting the process                                 |
| GET    | `/auth-code-requests/company/{companyNumber}/confirm`                 | Confirm this is the correct company                         |
| POST   | `/auth-code-requests/company/{companyNumber}/confirm`                 | Handle confirmation and continue                            |
| GET    | `/auth-code-requests/requests/{requestId}/officers`                   | Select an officer                                           |
| POST   | `/auth-code-requests/requests/{requestId}/officers`                   | Handle officer selection                                    |
| GET    | `/auth-code-requests/requests/{requestId}/confirm-officer`            | Confirm this is the correct officer                         |
| POST   | `/auth-code-requests/requests/{requestId}/confirm-officer`            | Handle confirmation and continue                            |
| GET    | `/auth-code-requests/requests/{requestId}/confirmation`               | Confirmation page                                           |
| GET    | `/auth-code-requests/accessibility-statement`                         | Who is eligible to use this service                         |
| GET    | `/auth-code-requests/company/{companyNumber}/cannot-use-this-service` | Cannot use this service                                     |

### Building a Docker container image

This project uses jib-maven-plugin to build Docker container images. To build a container image, run the following
command on the command line:

```bash
mvn compile jib:dockerBuild -Dimage=416670754337.dkr.ecr.eu-west-2.amazonaws.com/emergency-auth-code-web:latest
```
