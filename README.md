# Companies House Emergency Auth Code Web Service
The Companies House Web Service for requesting auth codes. This application is written using the [Spring Boot](http://projects.spring.io/spring-boot/) Java framework.

- Retrieves company information after using company lookup service.
- Displays selectable officers that can be chosen to receive an emergency auth code.

### Requirements
In order to run this Web app locally you will need to install:

- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
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


### Web Pages

Page                                     | Address
-----------------------------------------|-----------------------------
Request an authentication code to be sent to a home address | `/auth-code-requests/start`
Confirm this is the correct company                         | `/auth-code-requests/company/{companyNumber}/confirm`
Select an officer                                           | `/auth-code-requests/requests/{requestId}/officers`
Confirm this is the correct officer                         | `/auth-code-requests/requests/{requestId}/confirm-officer`
Confirmation page                                           | `/auth-code-requests/requests/{requestId}/confirmation`

### Building a Docker container image

This project uses jib-maven-plugin to build Docker container images. To build a container image, run the following
command on the command line:

```bash
mvn compile jib:dockerBuild -Dimage=169942020521.dkr.ecr.eu-west-1.amazonaws.com/local/emergency-auth-code-web:latest
```