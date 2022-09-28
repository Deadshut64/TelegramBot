package ru.kids;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("ru.kids.configuration")
public class KidBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(KidBotApplication.class, args);
    }
}
