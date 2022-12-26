package ru.kids.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.helpCommand.HelpCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kids.bot.service.ShowUsersCommand;
import ru.kids.configuration.BotProperties;

import java.util.List;
@Component
public class Bot extends TelegramLongPollingCommandBot {

    private final BotProperties properties;

    public Bot(BotProperties properties) {
        this.properties = properties;
        register(new ShowUsersCommand());
        register(new HelpCommand("help", "Помощь", ""));
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
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public void processNonCommandUpdate(Update update) {

    }

    @Override
    public void processInvalidCommandUpdate(Update update) {
        super.processInvalidCommandUpdate(update);
    }

    @Override
    public boolean filter(Message message) {
        return super.filter(message);
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
        for(Update u : updates){
            if (u.hasMessage()){
                long chatID = u.getMessage().getChatId();
                String message = u.getMessage().getText();
                switch (message){
                    case "/start":
                        testCommand(chatID,u.getMessage().getChat().getFirstName());
                        break;
                    default: sendMessage(chatID,"Sorry, something went wrong");
                }
            }
        }
    }
    private void testCommand(long chatID, String name){
        String answer = "Hi " + name + "you are stupid";
        sendMessage(chatID,answer);
    }
    private void sendMessage(long chatID, String text){
        SendMessage sendMessage = new SendMessage(String.valueOf(chatID),text);

        try {
            execute(sendMessage);
        }catch (TelegramApiException e){
        }
    }
}
