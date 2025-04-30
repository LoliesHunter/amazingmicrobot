package com.example.botClient;

import com.example.botClient.DTO.MessageRequest;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.BlockingHttpClient;

import io.micronaut.http.client.HttpClient;
import jakarta.inject.Singleton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Singleton
public class MicroBotClient extends TelegramLongPollingBot {

    //@Value("${message.saver.url}") //TODO СДЕЛАТЬ НОРМАЛЬНО И ПО ЧЕЛОВЕЧЕСКИ!!!!
    String urlSave = "http://message-saver-service:8081/messages";
    String urlExport = "http://message-processor-service:8082";

    private final BlockingHttpClient httpClient;

    private final URI serviceUri;
    private static final List<Long> adminUserIds = List.of(433993661L);

    //БРЕД КАКОЙ-ТО
    public MicroBotClient() throws URISyntaxException, MalformedURLException {
        HttpClient client = HttpClient.create(new URI(urlSave).toURL());
        this.httpClient = client.toBlocking();
        this.serviceUri = URI.create(urlSave);
    }

    @Override
    public String getBotUsername() {
        return "qnapsybot_bot";
    }

    @Override
    public String getBotToken() {
        return "8199399033:AAHyVb5uDWTrIZ0ePFIBLvf_dgfDn88lDE0";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String username = update.getMessage().getFrom().getUserName();
            String text = update.getMessage().getText();

            String messageText = update.getMessage().getText();
            Long userId = update.getMessage().getFrom().getId();
            Long chatId = update.getMessage().getChatId();

            if (messageText.equals("/export")) {
                if (adminUserIds.contains(userId)) {
                    sendExcelFile(chatId);
                } else {
                    sendText(chatId, "У вас нет прав на выполнение этой команды.");
                }
            }


            MessageRequest request = new MessageRequest(username, text);

            HttpRequest<MessageRequest> httpRequest = HttpRequest.POST(serviceUri, request);

            httpClient.exchange(httpRequest);
        }
    }

    private void sendExcelFile(Long chatId) {
        try {
            io.micronaut.http.client.HttpClient client = io.micronaut.http.client.HttpClient.create(new java.net.URL(urlExport));

            io.micronaut.http.HttpRequest<?> request = io.micronaut.http.HttpRequest.GET("/export")
                    .header("Authorization", "Bearer secretToken1234");

            byte[] fileBytes = client.toBlocking().retrieve(request, byte[].class);

            //Отправка в тг
            ByteArrayInputStream inputStream = new ByteArrayInputStream(fileBytes);
            SendDocument document = new SendDocument();
            document.setChatId(chatId.toString());
            document.setDocument(new InputFile(inputStream, "messages.xlsx"));

            execute(document);
        } catch (Exception e) {
            e.printStackTrace();
            sendText(chatId, "Ошибка при получении Excel файла: " + e.getMessage());
        }
    }

    private void sendText(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}