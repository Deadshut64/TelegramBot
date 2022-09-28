package ru.kids.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bot-configuration")
@Data
public class BotProperties {
    private String botName;
    private String botToken;
}
