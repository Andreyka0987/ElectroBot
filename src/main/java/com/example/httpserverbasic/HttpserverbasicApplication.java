package com.example.httpserverbasic;

import com.example.httpserverbasic.bot.TelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class HttpserverbasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(HttpserverbasicApplication.class, args);
		System.out.println("Server had started!");
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
			api.registerBot(new TelegramBot(System.getenv("key")));
			System.out.println("Bot had started!");
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
