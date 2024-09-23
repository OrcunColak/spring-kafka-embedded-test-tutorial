package com.colak.springkafkaembeddedtesttutorial.integrationtest;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(
        brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"},
        partitions = 1)
@Profile("test")
class EmbeddedKafkaIntegrationWithConsumerTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private DefaultKafkaConsumerFactory<String, String> defaultKafkaConsumerFactory;

    @Test
    void testKafkaMsgFlow () {
        System.out.println("Sending message to kafka topic");

        kafkaTemplate.send("TestTopic","Testing kafka msg flow");

        // By default, spring will create KafkaTemplate only with default producer factory and not with consumer factory.
        // We need to set consumer factory to use receive method
        kafkaTemplate.setConsumerFactory(defaultKafkaConsumerFactory);

        ConsumerRecord<String, String> record = kafkaTemplate.receive("TestTopic", 0,0);

        System.out.println("Message Received: " + record.value());
    }
}
