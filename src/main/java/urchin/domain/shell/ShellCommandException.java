package urchin.domain.shell;

public class ShellCommandException extends RuntimeException {

    public ShellCommandException(String message) {
        super(message);
    }

    public ShellCommandException(Exception e) {
        super(e);
    }

}
