package com.example.botClient;

import io.micronaut.runtime.Micronaut;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Application {


    public static void main(String[] args) throws Exception {

        Micronaut.run(Application.class, args);

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new MicroBotClient());

    }
}