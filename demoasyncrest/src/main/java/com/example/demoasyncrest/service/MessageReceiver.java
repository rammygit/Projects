package com.example.demoasyncrest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * generic Kafka receiver that will listen to the given topic and specific partition as per configuration.
 *
 * @author ramkumar
 */
@Service
public class MessageReceiver {

    private static final Logger logger = LoggerFactory.getLogger(MessageReceiver.class);


    @KafkaListener(topics = "${kafka.scan-send-topic}")
    public void listenScanResponse(@Payload String message) {
        logger.info("received message='{}'", message);
    }

}
