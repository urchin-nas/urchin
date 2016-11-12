package urchin.domain.cli.common;

public class CommandException extends RuntimeException {

    static final String PROCESS_RETURNED_EXIT_VALUE = "Process returned exit value: ";

    private final String commandName;
    private final Integer exitValue;

    public CommandException(String commandName, int exitValue) {
        super(PROCESS_RETURNED_EXIT_VALUE + exitValue);
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
