package ru.kids.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.kids.bot.Bot;
import ru.kids.bot.model.CustomerRepository;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(value = "bot-configuration.enabled-production", havingValue = "true", matchIfMissing = true)
public class ConfigBot {

    private final BotProperties botProperties;
    private final CustomerRepository customerRepository;

    @PostConstruct
    public void init() throws TelegramApiException {
        TelegramBotsApi botsApi;
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot(botProperties, customerRepository));
        } catch (TelegramApiException e) {
            log.error("error occurred: " + e.getMessage());
            throw e;
        }
    }
}
