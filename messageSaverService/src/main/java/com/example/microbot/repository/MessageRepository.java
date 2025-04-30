package com.example.microbot.repository;

import com.example.microbot.entity.MessageEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
}
