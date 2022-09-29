package ru.kids.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kids.configuration.BotProperties;

@Component
public class Bot extends TelegramLongPollingBot {

    private final BotProperties properties;

    public Bot(BotProperties properties) {
        this.properties = properties;
    }

    @Override
    public String getBotUsername() {
        return properties.getBotName();
    }

    @Override
    public String getBotToken() {
        return properties.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            long chatId = update.getMessage().getChatId();

            switch (message){
                case "/start":
                    try {
                        startCommand(chatId,update.getMessage().getChat().getFirstName());
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                default:
                    try {
                        sendMessage(chatId,"пу пу пу");
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

            }



        }
    }
    private void startCommand(long chatId, String name) throws TelegramApiException {
        String hello = "Привет " + name + "выбирай пол ребенка Пидр";
        sendMessage(chatId, hello);
    }
    private void sendMessage(long chatId, String text) throws TelegramApiException {
        SendMessage sm = new SendMessage();
        sm.setChatId(String.valueOf(chatId));
        sm.setText(text);

        try{
            execute(sm);
        } catch (TelegramApiException e){

        }

    }
}


