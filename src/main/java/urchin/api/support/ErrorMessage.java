package urchin.api.support;

public class ErrorMessage {

    private final String code;
    private String field;
    private String message;

    public ErrorMessage(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getField() {
        return field;
    }

    public ErrorMessage setField(String field) {
        this.field = field;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ErrorMessage setMessage(String message) {
        this.message = message;
        return this;
    }
}
