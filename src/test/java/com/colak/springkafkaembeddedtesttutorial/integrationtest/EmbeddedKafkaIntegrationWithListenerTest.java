package com.colak.springkafkaembeddedtesttutorial.integrationtest;

import com.colak.springkafkaembeddedtesttutorial.service.KafkaConsumer;
import com.colak.springkafkaembeddedtesttutorial.service.KafkaProducer;
import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
@Profile("test")
class EmbeddedKafkaIntegrationWithListenerTest {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Value("${test.topic}")
    private String topic;

    @Getter
    public static class TestKafkaConsumer extends KafkaConsumer {

        private final CountDownLatch latch = new CountDownLatch(1);
        private String payload;

        @KafkaListener(topics = "${test.topic}")
        public void receive(ConsumerRecord<?, ?> consumerRecord) {
            payload = consumerRecord.toString();
            latch.countDown();
        }
    }

    @TestConfiguration
    public static class KafkaConfigTest {
        @Bean
        public KafkaConsumer kafkaConsumer() {
            return new TestKafkaConsumer();
        }
    }

    @Test
    void testConsume(@Autowired TestKafkaConsumer testKafkaConsumer) throws InterruptedException {
        String data = "Sending with our own simple KafkaProducer";

        kafkaProducer.send(topic, data);

        boolean messageConsumed = testKafkaConsumer.getLatch().await(10, TimeUnit.SECONDS);
        assertTrue(messageConsumed);
        assertThat(testKafkaConsumer.getPayload()).contains(data);
    }
}
