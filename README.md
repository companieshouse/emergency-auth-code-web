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

| Key                            | Description                                     |
|--------------------------------|-------------------------------------------------|
| `EMERGENCY_AUTH_CODE_WEB_PORT` | The port of the emergency auth code web service |
| `HUMAN_LOG`                    | For human readable logs                         |
| `CHS_API_KEY`                  | API key for Companies House API calls           |
| `CDN_HOST`                     | CDN hostname for static assets                  |
| `CHS_URL`                      | Base URL of the CHS web service                 |
| `PIWIK_URL`                    | URL of the Piwik analytics service              |
| `PIWIK_SITE_ID`                | Site ID for Piwik analytics                     |
| `CONTACT_US_URL`               | URL for the contact us page                     |
| `DEVELOPER_URL`                | URL for the developer hub                       |

### Testing

```bash
# Run all tests
make test

# Run unit tests only
make test-unit

# Run a single test class
./mvnw test -Dtest=CompanyConfirmationPageControllerTest
```

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
