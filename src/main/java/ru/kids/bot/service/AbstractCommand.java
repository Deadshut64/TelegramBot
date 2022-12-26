package ru.kids.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.DefaultBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static ru.kids.utils.Utils.isPrivateChat;

@Slf4j
public abstract class AbstractCommand extends DefaultBotCommand {

    public AbstractCommand(CommandType command) {
        super("/" + command.getCommand(), command.getDescription());
    }

    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        if (!isPrivateChat(message) && skipMessage(message)) return;
        handleMessage(absSender, message, arguments);
    }

    protected Boolean skipMessage(Message message) {
        return false;
    }

    abstract void handleMessage(AbsSender absSender, Message message, String[] arguments);

    public void execute(
            AbsSender absSender,
            User user,
            Chat chat,
            Integer messageId,
            String[] arguments
    ) {
    }

    public void sendAnswer(
            AbsSender absSender, Long chatId, String description,
            String commandName, String userName
    ) {
        try {
            absSender.execute(new SendMessage(chatId.toString(), description));
        } catch (Exception e) {
            log.error("Not send message: Command $commandName. userName: $userName", e);
            sendError(absSender, chatId, commandName, userName);
        }
    }

    public void sendError(AbsSender absSender, Long chatId, String commandName, String userName) {
        try {
            absSender.execute(new SendMessage(chatId.toString(), "Похоже, я сломался. Попробуйте позже"));
        } catch (TelegramApiException e) {
            log.error("Not send message by error", e);
        }
    }

    public void sendError(AbsSender absSender, Long chatId, String commandName, String userName, Exception ex) {
        log.error("User error", ex);
        try {
            absSender.execute(new SendMessage(chatId.toString(), "Похоже, я сломался. Попробуйте позже"));
        } catch (TelegramApiException e) {
            log.error("Not send message by error", e);
        }
    }
}
