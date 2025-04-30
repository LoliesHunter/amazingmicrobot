package com.example.microbotProcessor.kafka;


import com.example.microbotProcessor.arango.MessageService;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Singleton
@KafkaListener(groupId = "${kafka.group-id.processor}")
@RequiredArgsConstructor
@Slf4j
public class MessageKafkaConsumer {
    private final MessageService messageService;



    @Topic("${kafka.topic.messages}")
    public void recieve(String message){
        log.info("Сообщение получено из Kafka: " + message);
        messageService.saveMessage(message);
    }
}
