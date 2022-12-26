package ru.kids.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.kids.bot.Bot;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "bot-configuration.enabled-production", havingValue = "true", matchIfMissing = true)
public class ConfigBot {

    @Autowired
    public Bot Bot(BotProperties botProperties){
        return new Bot(botProperties);
    }

    @PostConstruct
    public void init(Bot bot) {
        TelegramBotsApi botsApi;
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
