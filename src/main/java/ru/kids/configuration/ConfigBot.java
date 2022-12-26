package ru.kids.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.kids.bot.Bot;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(value = "bot-configuration.enabled-production", havingValue = "true", matchIfMissing = true)
public class ConfigBot {

    private final BotProperties botProperties;

    @PostConstruct
    public void init() {
        TelegramBotsApi botsApi;
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot(botProperties));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
