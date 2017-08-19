package urchin.controller.api;

import java.util.List;

public class ErrorDto {

    private final ErrorCode errorCode;
    private final String message;
    private final List<FieldErrorDto> fields;

    public ErrorDto(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.fields = null;
    }

    public ErrorDto(ErrorCode errorCode, String message, List<FieldErrorDto> fields) {
        this.errorCode = errorCode;
        this.message = message;
        this.fields = fields;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public List<FieldErrorDto> getFields() {
        return fields;
    }
}
