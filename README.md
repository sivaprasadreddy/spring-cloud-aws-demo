# Spring Cloud AWS Demo

A sample Spring Boot application using [Spring Cloud AWS](https://awspring.io/).

You might have been using [LocalStack](https://www.localstack.cloud/) for local development and testing.
But the original LocalStack community [project is archived](https://github.com/localstack/localstack) and 
going forward there will be a unified LocalStack image that requires an authentication token.

[Floci](https://floci.io/floci/) is a fast, free, and open-source local AWS service emulator and a drop-in replacement for LocalStack Community.

This sample application has two branches demonstrating how to use LocalStack and Floci:
* **main**: LocalStack
* **floci** Floci

The sample application uses S3 and SQS features for demonstration purposes.