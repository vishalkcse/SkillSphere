package com.skillsphere.config;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaCertificationProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendRenewalEvent(String message) {
        try {
            kafkaTemplate.send("certification-renewal", message);
        } catch (Exception e) {
            System.err.println("Kafka producer notice: " + e.getMessage());
        }
    }
}
