package dev.sivalabs.demo;

import io.floci.testcontainers.FlociContainer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;

import java.util.UUID;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfig {

    static final String BUCKET_NAME = UUID.randomUUID().toString();
    static final String QUEUE_NAME = UUID.randomUUID().toString();

    @Bean
    @ServiceConnection
    FlociContainer flociContainer() throws Exception {
        var floci = new FlociContainer();
        floci.start();
        floci.execInContainer("aws",
                "--endpoint-url", "http://localhost:4566",
                "s3", "mb", "s3://" + BUCKET_NAME);
        floci.execInContainer("aws",
                "--endpoint-url", "http://localhost:4566",
                "sqs", "create-queue", "--queue-name",
                QUEUE_NAME
        );
        return floci;
    }

    @Bean
    DynamicPropertyRegistrar dynamicPropertyRegistrar() {
        return (registry) -> {
            registry.add("app.bucket", () -> BUCKET_NAME);
            registry.add("app.queue", () -> QUEUE_NAME);
        };
    }
}
