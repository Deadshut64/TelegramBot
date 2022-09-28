package ru.kids.utils;

import org.telegram.telegrambots.meta.api.objects.Message;

public class Utils {

    public static boolean isPrivateChat(Message message) {
        return message != null
                && message.getChat().isUserChat()
                && message.getText() != null;
    }
}
