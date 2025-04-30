package com.example.microbot.controller;


import com.example.microbot.entity.MessageEntity;
import com.example.microbot.services.MessageService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Controller("/messages")
@RequiredArgsConstructor
@Tag(name = "messages")
public class MessageController {

    private final MessageService messageService;

    @Operation(summary = "Принимает сообщения от бота ")
    @Post
    public MessageEntity receiveMessage (@Body MessageRequest messageRequest){
        return messageService.saveMessage(
                messageRequest.getUsername(),
                messageRequest.getText()
        );
    }

}
