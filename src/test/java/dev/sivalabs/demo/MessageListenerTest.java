package dev.sivalabs.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.Duration;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@Import(TestcontainersConfig.class)
class MessageListenerTest {

    @Autowired
    StorageService storageService;

    @Autowired
    MessageSender publisher;

    @Autowired
    ApplicationProperties properties;

    @Test
    void shouldHandleMessageSuccessfully() {
        Message message = new Message(UUID.randomUUID(), "Hello World");
        publisher.publish(properties.queue(), message);

        await()
                .pollInterval(Duration.ofSeconds(2))
                .atMost(Duration.ofSeconds(10))
                .ignoreExceptions()
                .untilAsserted(() -> {
                    String msg = storageService.downloadAsString(
                            properties.bucket(),
                            message.uuid().toString()
                    );
                    assertThat(msg).isEqualTo("Hello World");
                });
    }
}
