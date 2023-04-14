package ru.kids.bot;

import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.helpCommand.HelpCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kids.bot.model.Customer;
import ru.kids.bot.model.CustomerRepository;
import ru.kids.configuration.BotProperties;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Bot extends TelegramLongPollingCommandBot {

  private final CustomerRepository customerRepository;
  private final BotProperties properties;

  public Bot(BotProperties properties, CustomerRepository customerRepository) {
    this.properties = properties;
    this.customerRepository = customerRepository;
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
    for (Update u : updates) {
      if (u.hasMessage()) {
        long chatID = u.getMessage().getChatId();
        String message = u.getMessage().getText();
        switch (message) {
          case "/start":
            registerCustomer(u.getMessage());
            startCommand(chatID, u.getMessage().getChat().getFirstName());
            keyboardGender(chatID,u.getMessage().getText());
            break;
          case "/help":
            helpCommand(chatID);
            break;
          case "/filtersizeS":
          case "/list":
            keyboardTest(chatID,u.getMessage().getText());
          case "/buy":
          case "/filtersizeI":
          case "/filterseason":
          case "/deletedata":
          case "/man":
            filterMan(chatID);
            break;
          case "/woman":
            filterWoman(chatID);
            break;
          case "/mydata":
            break;
          default:
            sendMessage(chatID, "Извините, данная команда не поддерживается");
        }
      }
    }
  }

  private void registerCustomer(Message message) {

    if (customerRepository.findById(message.getChatId()).isEmpty()) {

      var chatId = message.getChatId();
      var chat = message.getChat();

      Customer customer = new Customer();
      customer.setChat_id(chatId);
      if (customer.getCustomer_name() == null) {
        customer.setCustomer_name("customer without a name");
        customer.setRegistration(new Timestamp(System.currentTimeMillis()));
        customerRepository.save(customer);
        log.info("Пользователь " + customer + "сохранен");
      }
      customer.setCustomer_name(chat.getUserName());
      customer.setRegistration(new Timestamp(System.currentTimeMillis()));
      customerRepository.save(customer);
      log.info("Пользователь " + customer + "сохранен");
    }

  }

  private void startCommand(long chatID, String name) {
    String answer = EmojiParser.parseToUnicode("Здраствуйте " + name + " :blush:, выберите пожалуйста пол ребенка ");
    if (name == null) {
      sendMessage(chatID, EmojiParser.parseToUnicode("Здраствуйте :blush:, выберите пожалуйста пол ребенка "));
    } else {
      sendMessage(chatID, answer);
    }
  }

  private void helpCommand(long chatID) {
    String answer = "По всем не решенным вопросам пишите в телеграмм @kidsbots64\n\n" +
            "Пункты меню:\n" +
            "/start - фильтрует пол ребенка\n\n" +
            "/filtersizeS - фильтрует товары по гендеру\n\n" +
            "/filtersizeI - фильтрует товары только по размеру обуви\n\n" +
            "/filterseason - фильтрует товары только по сезону\n\n" +
            "/mydata - показывает текущие данные пользователя\n\n " +
            "/deletedata - Удаляет данные о пользователе и заказе\n\n" +
            "/list - показывает список всех товаров\n\n" +
            "/buy - Купить товар\n\n" +
            "/help - Помощь";
    sendMessage(chatID, answer);
  }

  private void sendMessage(long chatID, String text) {
    SendMessage sendMessage = new SendMessage(String.valueOf(chatID), text);
    try {
      execute(sendMessage);
    } catch (TelegramApiException e) {
      log.error("error occurred: " + e.getMessage());
    }
  }

  private void keyboardGender(long chatID, String text) {
    SendMessage sendMessage = new SendMessage(String.valueOf(chatID), text);
    InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
     List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
      List<InlineKeyboardButton> rowInLine = new ArrayList<>();
         var buttonMan = new InlineKeyboardButton();
             buttonMan.setText("Мальчик");
             buttonMan.setCallbackData("Man");
         var buttonWoman = new InlineKeyboardButton();
             buttonWoman.setText("Девочка");
             buttonWoman.setCallbackData("Woman");
         rowInLine.add(buttonMan);
         rowInLine.add(buttonWoman);
        rowsInLine.add(rowInLine);
    keyboardMarkup.setKeyboard(rowsInLine);
    sendMessage.setReplyMarkup(keyboardMarkup);
    try {
      execute(sendMessage);
    } catch (TelegramApiException e) {
      log.error("error occurred: " + e.getMessage());
    }
  }
  private void keyboardTest(long chatID, String text) {
    SendMessage sendMessage = new SendMessage(String.valueOf(chatID), text);
    ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
    List<KeyboardRow> keyboardRows = new ArrayList<>();
    KeyboardRow row = new KeyboardRow();
    row.add("другая кнопка");
    row.add("другая кнопка2");
    keyboardRows.add(row);
    replyKeyboard.setKeyboard(keyboardRows);
    sendMessage.setReplyMarkup(replyKeyboard);
    try {
      execute(sendMessage);
    } catch (TelegramApiException e) {
      log.error("error occurred: " + e.getMessage());
    }
  }

  private void filterMan(long chatId){
    SendMessage message = new SendMessage();
    message.setChatId(chatId);

  }
  private void filterWoman(long chatId){
    SendMessage message = new SendMessage();
    message.setChatId(chatId);
  }

}
