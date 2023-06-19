# api-hub-performance-tests

Performance test suite for The API Hub, using [performance-test-runner](https://github.com/hmrc/performance-test-runner) under the hood.

## Documentation
See [Performance Test - The API Hub](https://confluence.tools.tax.service.gov.uk/display/AH/Performance+Testing+-+The+API+Hub) on Confluence for configuration details and test output analysis.

## Pre-requisites

### Services

Start Mongo Docker container, through the Docker Dashboard or as follows:

```bash
docker run --rm -d --name mongo -d -p 27017:27017 mongo:4.0
```

Start `API_HUB_ALL` services as follows:

```bash
sm2 --start API_HUB_ALL
```

Ensure all services have started successfully before running the tests:

```bash
sm2 --status
```

### Data when running locally

When running locally, you will need to create an application and update the values in `services-local.conf`.

### Logging

The default log level for all HTTP requests is set to `WARN`. Configure [logback.xml](src/test/resources/logback.xml) to update this if required.



## Tests

### Testing against The API Hub running locally vs in Staging

For public MDTP microservices, the advice is not to run against staging from your local machine and instead use a Jenkins job. However, Jenkins cannot access an admin frontend. Therefore we will run against staging from the local machine.

This project still has the ability to run the performace tests against a locally running version of The API Hub, as well as againsr Staging.

### Against The API Hub running locally

This runs against the api-hub-frontend from Service Manager.

Remember, you need to update the values in `services-local.conf`.

Run smoke test (locally) as follows:

```bash
sbt -Dperftest.runSmokeTest=true -DrunLocal=true gatling:test
```

Run full performance test (locally) as follows:

```bash
sbt -DrunLocal=true gatling:test
```

### Against The API Hub in Staging

Run smoke test (locally) as follows:

```bash
sbt -Dperftest.runSmokeTest=true -DrunLocal=false gatling:test
```

Run smoke test (staging) as follows:

```bash
sbt -Dperftest.runSmokeTest=true -DrunLocal=false gatling:test
```

## Scalafmt

Check all project files are formatted as expected as follows:

```bash
sbt scalafmtCheckAll scalafmtCheck
```

Format `*.sbt` and `project/*.scala` files as follows:

```bash
sbt scalafmtSbt
```

Format all project files as follows:

```bash
sbt scalafmtAll
```

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
