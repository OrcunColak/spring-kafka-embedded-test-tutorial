package com.colak.springkafkaembeddedtesttutorial.producertest;

import com.colak.springkafkaembeddedtesttutorial.service.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class KafkaProducerTest {

    @Autowired
    private KafkaProducer producer;

    @Value("${test.topic}")
    private String topic;

    @Test
    void sendMessage() {
        boolean result = producer.send(topic, "alo");
        assertTrue(result);
    }
}
