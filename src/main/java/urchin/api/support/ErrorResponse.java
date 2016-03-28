package urchin.api.support;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {

    private final String code;
    private String field;
    private String message;

    @JsonCreator
    public ErrorResponse(@JsonProperty("code") String code) {
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
