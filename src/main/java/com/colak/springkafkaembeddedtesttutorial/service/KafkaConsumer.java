package com.colak.springkafkaembeddedtesttutorial.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component

@Getter
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "${test.topic}")
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        String payload = consumerRecord.toString();
        log.info("received payload='{}'", payload);

        Object value = consumerRecord.value();
        log.info("received value='{}'", value);
    }
}
