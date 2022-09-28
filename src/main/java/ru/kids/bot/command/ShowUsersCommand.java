package ru.kids.bot.command;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class ShowUsersCommand extends AbstractCommand {
    public ShowUsersCommand() {
        super(CommandType.SHOW_USERS);
    }

    @Override
    void handleMessage(AbsSender absSender, Message message, String[] arguments) {

    }
}
