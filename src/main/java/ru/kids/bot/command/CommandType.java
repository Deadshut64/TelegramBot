package ru.kids.bot.command;

public enum CommandType {
    SHOW_USERS("show_users", "Все пользователи");

    private final String command;
    private final String description;

    CommandType(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }
}
