package com.skillsphere.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class KafkaCertificationProducer {

    @Autowired(required = false)
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendRenewalEvent(String message) {
        System.out.println("[Certification Event Logged] " + message);
        if (kafkaTemplate != null) {
            CompletableFuture.runAsync(() -> {
                try {
                    kafkaTemplate.send("certification-renewal", message);
                } catch (Throwable t) {
                    // Non-blocking fallback for local standalone mode
                }
            });
        }
    }
}
