# DD4J-test-application

Spring Boot application for running DD4J for testing purposes.

## Prerequisites

For building and running this application, Java 17 is needed.

## Container and signature validation

If enabled, the application allows to validate signed containers / signatures.

The functionality can be enabled via the following configuration parameter:

| Configuration parameter | Required | Description | Example |
| ----------------------- | -------- | ----------- | ------- |
| `validation.enabled` | NO | Whether to enable the validation functionality and endpoints. Defaults to `false` if not specified. | `true` |

### Available endpoints

The application provides the following endpoint for validation:

```
POST /validate
```

The validation endpoint consumes JSON requests with the following parameters:

| Request parameter | Required | Description | Example |
| ----------------- | -------- | ----------- | ------- |
| `container` | YES | The container to validate in base64 encoded format. | `Q29udGFpbmVyIGJ5dGVzIGVuY29kZWQgaW4gYmFzZTY0IGZvcm1hdC4=` |

The validation endpoint produces JSON responses with the following parameters:

| Response parameter | Always present | Description | Example |
| ------------------ | -------------- | ----------- | ------- |
| `valid` | YES | Boolean flag indicating whether the validated container is valid. | `true` |
| `containerErrors` | NO | List of container errors. | - |
| `containerErrors[i].message` | YES | Container error message. | `Container has a problem` |
| `containerErrors[i].errorCode` | NO | Container error code number. | `13` |
| `containerWarnings` | NO | List of container warnings. | - |
| `containerWarnings[i].message` | YES | Container warning message. | `Container has a problem` |
| `containerWarnings[i].errorCode` | NO | Container warning code number. | `13` |
| `errors` | NO | List of signature errors. | - |
| `errors[i].message` | YES | Signature error message. | `Signature has a problem` |
| `errors[i].signatureId` | NO | Signature ID. | `Signature has a problem` |
| `errors[i].errorCode` | NO | Signature error code number. | `13` |
| `warnings` | NO | List of signature warnings. | - |
| `warnings[i].message` | YES | Signature warning message. | `Signature has a problem` |
| `warnings[i].signatureId` | NO | Signature ID. | `Signature has a problem` |
| `warnings[i].errorCode` | NO | Signature warning code number. | `13` |
| `validationReport` | NO | XML validation report in base64 encoded format. | `PFNpbXBsZVJlcG9ydD4=` |

### Configuration parameters

If validation is enabled in the application, then the following configuration parameters can be configured:

| Configuration parameter | Required | Description | Example |
| ----------------------- | -------- | ----------- | ------- |
| `validation.configuration.mode` | YES | DD4J configuration mode for validation. Available options are `PROD` and `TEST`. | `TEST` |
| `validation.configuration.implementations.{propertyName}` | NO | Implementing class of the specified DD4J configuration property.<sup>IMPL</sup> | `ee.test_gov.dd4j.app.common.factory.InMemoryCachingAiaSourceFactory` |
| `validation.configuration.thread-executor.core-pool-size` | NO | Validation executor service core pool size. Defaults to `0` if not specified. | `10` |
| `validation.configuration.thread-executor.maximum-pool-size` | NO | Validation executor service maximum pool size. Defaults to `1` if not specified. | `100` |
| `validation.configuration.thread-executor.keep-alive-time` | NO | Validation executor service thread keep-alive time in ISO-8601 duration format `PnDTnHnMn.nS`. Defaults to 1 minute if not specified. | `PT5M` |

<sup>IMPL</sup> Enables to configure DD4J configuration to use a specific implementation for the specified property.
For example, `aiaSourceFactory` implementation can be configured as follows:
`validation.configuration.implementations.aiaSourceFactory=ee.test_gov.dd4j.app.common.factory.InMemoryCachingAiaSourceFactory`.
<br>**NB:** the specified implementation must have a constructor that takes one `org.digidoc4j.Configuration` argument!

## Running the application in Docker

1. Build the Docker image
   ```shell
   ./mvnw spring-boot:build-image -DskipTests
   ```
2. Start the application
   ```shell
   docker-compose up
   ```

The application is accessible on http://localhost:8080 .
You can view the logs for all the running containers at http://localhost:9080 .
