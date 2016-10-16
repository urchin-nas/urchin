package urchin.domain.cli;

public class CommandException extends RuntimeException {

    private final String commandName;

    public CommandException(String commandName, String message) {
        super(message);
        this.commandName = commandName;
    }

    public CommandException(String commandName, Exception e) {
        super(e);
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
