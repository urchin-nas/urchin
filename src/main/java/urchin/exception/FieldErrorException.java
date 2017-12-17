package urchin.exception;

import java.util.ArrayList;
import java.util.List;

public class FieldErrorException extends RuntimeException {

    private final String field;
    private final List<String> messages;

    public FieldErrorException(String field, String message) {
        this.messages = new ArrayList<>();
        this.messages.add(message);
        this.field = field;
    }

    public FieldErrorException(String field, List<String> messages) {
        this.field = field;
        this.messages = messages;
    }

    public String getField() {
        return field;
    }

    public List<String> getMessages() {
        return messages;
    }
}
