package urchin.api.support;

public class ErrorResponse {

    private String code;
    private String field;
    private String message;

    private ErrorResponse() {
    }

    public ErrorResponse(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getField() {
        return field;
    }

    public ErrorResponse setField(String field) {
        this.field = field;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ErrorResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}
