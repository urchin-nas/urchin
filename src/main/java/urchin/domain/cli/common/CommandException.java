package urchin.domain.cli.common;

public class CommandException extends RuntimeException {

    private final String commandName;
    private final Integer exitValue;

    public CommandException(String commandName, int exitValue) {
        super("Process returned exit value: " + exitValue);
        this.commandName = commandName;
        this.exitValue = exitValue;
    }

    public CommandException(String commandName, Exception e) {
        super(e);
        this.commandName = commandName;
        this.exitValue = null;
    }

    public String getCommandName() {
        return commandName;
    }

    public Integer getExitValue() {
        return exitValue;
    }
}
