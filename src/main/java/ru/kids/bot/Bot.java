package ru.kids.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.helpCommand.HelpCommand;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kids.bot.model.Customer;
import ru.kids.bot.model.CustomerRepository;
import ru.kids.bot.service.ShowUsersCommand;
import ru.kids.configuration.BotProperties;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class Bot extends TelegramLongPollingCommandBot {

    @Autowired
    private CustomerRepository customerRepository;
    private final BotProperties properties;

    public Bot(BotProperties properties) {
        this.properties = properties;
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
                        registerCustomer(u.getMessage());
                        startCommand(chatID,u.getMessage().getChat().getFirstName());
                        break;
                    case "/help":
                        helpCommand(chatID);
                        break;
                    case "/list":
                    case "/buy":
                    case "/filtersizeI":
                    case "/filterseason":
                    case "/filtersizeS":
                    case "/deletedata":
                    case "/mydata":
                        break;
                    default: sendMessage(chatID,"Извините, данная команда не поддерживается");
                }
            }
        }
    }
    private void registerCustomer(Message message){

        if(customerRepository.findById(message.getChatId()).isEmpty()){

            var chatId = message.getChatId();
            var chat = message.getChat();

            Customer customer = new Customer();
            customer.setChat_id(chatId);
            customer.setCustomer_name(chat.getUserName());
            customer.setRegistration(new Timestamp(System.currentTimeMillis()));

            customerRepository.save(customer);
            log.info("Пользователь " + customer + "сохранен");
        }

    }
    private void startCommand(long chatID, String name) {
        String answer = "Здраствуйте " + name + ", выберите пожалуйста пол ребенка";
        sendMessage(chatID, answer);
    }
        private void helpCommand(long chatID) {
            String answer = "По всем не решенным вопросам пишите в телеграмм @kidsbots64\n\n" +
                    "Пункты меню:\n" +
                    "/start - фильтрует пол ребенка\n\n" +
                    "/filtersizeS - фильтрует товары только по размеру обуви\n\n" +
                    "/filtersizeI - фильтрует товары только по размеру обуви\n\n" +
                    "/filterseason - фильтрует товары только по сезону\n\n" +
                    "/mydata - показывает текущие данные пользователя\n\n " +
                    "/deletedata - Удаляет данные о пользователе и заказе\n\n" +
                    "/list - показывает список всех товаров\n\n" +
                    "/buy - Купить товар\n\n" +
                    "/help - Помощь";
            sendMessage(chatID,answer);
    }
    private void sendMessage(long chatID, String text){
        SendMessage sendMessage = new SendMessage(String.valueOf(chatID),text);

        try {
            execute(sendMessage);
        }catch (TelegramApiException e){
            log.error("error occurred: " + e.getMessage());
        }
    }
}
