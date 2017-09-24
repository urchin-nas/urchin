package urchin.controller.api;

import java.util.List;
import java.util.Map;

public class ErrorDto {

    private final ErrorCode errorCode;
    private final String message;
    private final Map<String, List<String>> fieldErrors;

    public ErrorDto(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.fieldErrors = null;
    }

    public ErrorDto(ErrorCode errorCode, String message, Map<String, List<String>> fieldErrors) {
        this.errorCode = errorCode;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, List<String>> getFieldErrors() {
        return fieldErrors;
    }
}
