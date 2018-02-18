package urchin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import urchin.controller.api.ErrorCode;
import urchin.controller.api.ErrorResponse;
import urchin.controller.api.ImmutableErrorResponse;
import urchin.exception.CommandException;
import urchin.exception.FieldErrorException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCause;

@RestControllerAdvice
public class ControllerAdvice {

    public static final String VALIDATION_ERROR_MESSAGE = "validation error";
    private static final String VALIDATION_ERROR_WHILE_HANDLING_REQUEST = "Validation error while handling request: {}";
    private static final String EXCEPTION_WHILE_HANDLER_REQUEST = "Exception while handler request: {}";
    static final String UNEXPECTED_ERROR_MESSAGE = "an unexpected error occurred. See logs for details";
    private static final Logger log = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleValidationError(MethodArgumentNotValidException e, WebRequest webRequest) {
        log.warn(VALIDATION_ERROR_WHILE_HANDLING_REQUEST, webRequest);

        Map<String, List<String>> fieldErrors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            List<String> errors = fieldErrors.computeIfAbsent(fieldError.getField(), m -> new ArrayList<>());
            errors.add(fieldError.getDefaultMessage());
        });

        return ImmutableErrorResponse.builder()
                .errorCode(ErrorCode.VALIDATION_ERROR)
                .message(VALIDATION_ERROR_MESSAGE)
                .fieldErrors(fieldErrors)
                .build();
    }

    @ExceptionHandler(value = {FieldErrorException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleFieldErrorException(FieldErrorException e, WebRequest webRequest) {
        log.warn(VALIDATION_ERROR_WHILE_HANDLING_REQUEST, webRequest);

        Map<String, List<String>> fieldErrors = new HashMap<>();
        fieldErrors.put(e.getField(), e.getMessages());

        return ImmutableErrorResponse.builder()
                .errorCode(ErrorCode.VALIDATION_ERROR)
                .message(VALIDATION_ERROR_MESSAGE)
                .fieldErrors(fieldErrors)
                .build();
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorResponse handleIllegalArgumentException(IllegalArgumentException e, WebRequest webRequest) {
        log.warn(EXCEPTION_WHILE_HANDLER_REQUEST, webRequest, e);
        return ImmutableErrorResponse.builder()
                .errorCode(ErrorCode.ILLEGAL_ARGUMENT)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = {IllegalStateException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorResponse handleIllegalstateException(IllegalStateException e, WebRequest webRequest) {
        log.warn(EXCEPTION_WHILE_HANDLER_REQUEST, webRequest, e);
        return ImmutableErrorResponse.builder()
                .errorCode(ErrorCode.ILLEGAL_STATE)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = {CommandException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorResponse handleCommandException(CommandException e, WebRequest webRequest) {
        log.warn(EXCEPTION_WHILE_HANDLER_REQUEST, webRequest, e);
        return ImmutableErrorResponse.builder()
                .errorCode(ErrorCode.COMMAND_EXECUTION_ERROR)
                .message(String.format("Failed to execute %s. %s", e.getCommand().getSimpleName(), e.getMessage()))
                .build();
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ErrorResponse> handleUnexpectedException(Exception e, WebRequest webRequest) {
        Throwable rootCause = getRootCause(e);
        if (rootCause instanceof FieldErrorException) {
            ErrorResponse errorResponse = handleFieldErrorException((FieldErrorException) rootCause, webRequest);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        log.error(EXCEPTION_WHILE_HANDLER_REQUEST, webRequest, e);
        return new ResponseEntity<>(ImmutableErrorResponse.builder()
                .errorCode(ErrorCode.UNEXPECTED_ERROR)
                .message(UNEXPECTED_ERROR_MESSAGE)
                .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
