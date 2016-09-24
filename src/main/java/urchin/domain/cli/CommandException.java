package urchin.domain.cli;

class CommandException extends RuntimeException {

    private final String commandName;

    CommandException(String commandName, String message) {
        super(message);
        this.commandName = commandName;
    }

    CommandException(String commandName, Exception e) {
        super(e);
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
