package dev.sivalabs.demo;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.testcontainers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.UUID;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfig {

    static final String BUCKET_NAME = UUID.randomUUID().toString();
    static final String QUEUE_NAME = UUID.randomUUID().toString();

    @Bean
    @ServiceConnection
    LocalStackContainer localStackContainer() throws Exception {
        LocalStackContainer localStack = new LocalStackContainer(DockerImageName.parse("localstack/localstack:4.0"));
        localStack.start();
        localStack.execInContainer("awslocal", "s3", "mb", "s3://" + BUCKET_NAME);
        localStack.execInContainer(
                "awslocal",
                "sqs",
                "create-queue",
                "--queue-name",
                QUEUE_NAME
        );
        return localStack;
    }

    @Bean
    DynamicPropertyRegistrar dynamicPropertyRegistrar() {
        return (registry) -> {
            registry.add("app.bucket", () -> BUCKET_NAME);
            registry.add("app.queue", () -> QUEUE_NAME);
        };
    }
}
