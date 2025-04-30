package com.example.microbot.services;

import com.example.microbot.entity.MessageEntity;
import com.example.microbot.kafka.MessageKafkaProducer;
import com.example.microbot.repository.MessageRepository;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Singleton
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageKafkaProducer kafkaProducer;

    public MessageEntity saveMessage(String username, String text){
        MessageEntity message = MessageEntity.builder()
                .username(username)
                .text(text)
                .createdAt(LocalDateTime.now())
                .build();

        MessageEntity savedMessage = messageRepository.save(message);

        kafkaProducer.send("Message from " + username + ": " + text);

        return savedMessage;
    }


}
