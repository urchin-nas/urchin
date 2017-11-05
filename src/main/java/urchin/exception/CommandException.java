package urchin.exception;

public class CommandException extends RuntimeException {

    public static final String PROCESS_RETURNED_EXIT_VALUE = "Process returned exit value: ";

    private final Class command;
    private final Integer exitValue;

    public CommandException(Class command, int exitValue) {
        super(PROCESS_RETURNED_EXIT_VALUE + exitValue);
        this.command = command;
        this.exitValue = exitValue;
    }

    public CommandException(Class command, Exception e) {
        super(e);
        this.command = command;
        this.exitValue = null;
    }

    public Class getCommand() {
        return command;
    }

    public Integer getExitValue() {
        return exitValue;
    }
}
