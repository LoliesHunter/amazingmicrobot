package com.example.microbotProcessor.arango;


import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.MultiDocumentEntity;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class MessageService {

    private final ArangoDatabase database;


    @PostConstruct
    public void init() {
        if (!database.collection("messages").exists()) {
            database.createCollection("messages");
        }
    }

    public void saveMessage(String message) {
        BaseDocument document = new BaseDocument();
        document.addAttribute("content", message);
        document.addAttribute("timestamp", System.currentTimeMillis());
        database.collection("messages").insertDocument(document);
    }

    public List<MessageDTO> getAllMessages() {

        var collection = database.collection("messages");

        if (!collection.exists()) {
            return List.of(); // коллекции нет, вернуть пустой список
        }

        MultiDocumentEntity<BaseDocument> entity = database.collection("messages").getDocuments(null, BaseDocument.class);

        return entity.getDocuments()
                .stream()
                .map(doc -> new MessageDTO(
                        (String) doc.getAttribute("content"),
                        (Long) doc.getAttribute("timestamp")
                ))
                .collect(Collectors.toList());
    }

    public List<MessageDTO> getAllSortedByTimeMessages() {

        var collection = database.collection("messages");

        if (!collection.exists()) {
            return List.of(); // коллекции нет, вернуть пустой список
        }

        MultiDocumentEntity<BaseDocument> entity = database.collection("messages").getDocuments(null, BaseDocument.class);

        return entity.getDocuments()
                .stream()
                .map(doc -> new MessageDTO(
                        (String) doc.getAttribute("content"),
                        (Long) doc.getAttribute("timestamp")
                ))
                .sorted(Comparator.comparing(MessageDTO::getTimestamp).reversed())
                .collect(Collectors.toList());
    }

    public List<MessageDTO> getLimitedSortedByTimeMessages(int count) {

        var collection = database.collection("messages");

        if (!collection.exists()) {
            return List.of(); // Коллекции нет
        }

        try {
            var cursor = database.query(
                    "FOR doc IN messages SORT doc.timestamp DESC LIMIT @count RETURN doc",
                    Map.of("count", count),
                    null,
                    BaseDocument.class
            );

            List<BaseDocument> documents = cursor.asListRemaining();

            return documents.stream()
                    .filter(doc -> !(doc.getAttribute("content").equals("/export") || doc.getAttribute("content").equals("/start")))
                    .map(doc -> new MessageDTO(
                            (String) doc.getAttribute("content"),
                            (Long) doc.getAttribute("timestamp")
                    ))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.out.println("Ошибка при получении сообщений: " + e.getLocalizedMessage());
            return List.of();
        }
    }
}

