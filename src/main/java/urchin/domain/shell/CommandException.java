package urchin.domain.shell;

public class CommandException extends RuntimeException {

    public CommandException(String message) {
        super(message);
    }

    public CommandException(Exception e) {
        super(e);
    }

}
