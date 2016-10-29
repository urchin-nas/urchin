package urchin.domain.cli;

public class CommandException extends RuntimeException {

    private final String commandName;
    private final Integer errorCode;

    public CommandException(String commandName, int errorCode) {
        super("Process returned code: " + errorCode);
        this.commandName = commandName;
        this.errorCode = errorCode;
    }

    public CommandException(String commandName, Exception e) {
        super(e);
        this.commandName = commandName;
        this.errorCode = null;
    }

    public String getCommandName() {
        return commandName;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
