package com.example.demoasyncrest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {


    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    private static final String ATTACHMENT_TOPIC_SEND = "my-scan-topic";

    private static final String ATTACHMENT_TOPIC_RECEIVE = "my-scan-topic-receive";

    /**
     * send the message to the broker.
     * @param topicName
     */
    public void sendMessage(String topicName,String message){

        //for now hard-code the topic name.

        kafkaTemplate.send(ATTACHMENT_TOPIC_SEND,message);
    }
}
