package ru.kids;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class BotConfigTest {

    @Test
    void checkBotConfigurationNameAndToken() {
        String botName = System.getenv("BOT_NAME");
        String botToken = System.getenv("BOT_TOKEN");
        Assertions.assertNotNull(botName, "Не установлена переменная среды 'BOT_NAME'");
        Assertions.assertNotNull(botToken, "Не установлена переменная среды 'BOT_TOKEN'");
        Assertions.assertFalse(botName.isBlank(), "Не установлена переменная среды 'BOT_NAME'");
        Assertions.assertFalse(botToken.isBlank(), "Не установлена переменная среды 'BOT_TOKEN'");
    }
}
