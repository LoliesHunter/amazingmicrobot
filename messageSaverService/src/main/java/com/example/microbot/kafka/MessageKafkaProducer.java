package com.example.microbot.kafka;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient
public interface MessageKafkaProducer {

    @Topic("${kafka.topic.messages}")
    void send(String message);
}
