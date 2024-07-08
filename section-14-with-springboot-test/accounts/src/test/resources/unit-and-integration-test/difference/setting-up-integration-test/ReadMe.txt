Also make aure to annotate other class with @Profile("!test"),
This ensures that any beans or configurations specific to the test profile are activated, and since AccountsController
is marked to be excluded from the test profile, it won't be loaded.

To ensure that your integration test only loads application-test.yml from src/test/resources and does not attempt to
connect to external services like Eureka Server or Config Server, you can follow these steps:

1. Use @ActiveProfiles("test") Annotation: You've already used this annotation in your test class, which is correct.
This tells Spring Boot to use application-test.yml for your tests.

2. Configure application-test.yml: Ensure that this file contains the necessary configurations to disable or mock external services.
For example, to disable a Eureka client in your tests, you can set:

spring:
  application:
    name: accounts-test
  cloud:
    config:
      enabled: false
    discovery:
      enabled: false

eureka:
  client:
    enabled: false

# Mock or disable other external services as needed


Ensure this file is located at src/test/resources/application-test.yml.
Spring Boot will automatically merge configurations from application.yml and application-test.yml,
with properties in application-test.yml taking precedence during tests.

