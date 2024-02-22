package com.colak.springkafkaembeddedtesttutorial.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component

@Getter
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public boolean send(String topic, String payload) {
        log.info("sending payload='{}' to topic='{}'", payload, topic);
        boolean result = true;

        try {
            kafkaTemplate.send(topic, payload).get();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            result = false;
        } catch (ExecutionException exception) {
            result = false;
        }
        return result;

    }

}
