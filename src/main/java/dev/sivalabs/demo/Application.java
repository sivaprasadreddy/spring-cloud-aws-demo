package dev.sivalabs.demo;

import io.awspring.cloud.s3.S3Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.services.sqs.model.*;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner initializer(
            S3Template s3Template,
            ApplicationProperties properties) {

        return (args) -> {
            if(!s3Template.bucketExists(properties.bucket())) {
                String bucketName = s3Template.createBucket(properties.bucket());
                log.info("S3 bucket '{}' is created", bucketName);
            }
        };
    }
}
