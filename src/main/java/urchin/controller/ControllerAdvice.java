package urchin.controller;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
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

import java.util.*;

import static java.util.Collections.singletonList;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCause;

@RestControllerAdvice
public class ControllerAdvice {

    private final static Logger LOG = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleValidationError(MethodArgumentNotValidException e, WebRequest webRequest) {
        LOG.debug("Validation error while handling request: {}", webRequest);

        Map<String, List<String>> fieldErrors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            List<String> errors = fieldErrors.computeIfAbsent(fieldError.getField(), m -> new ArrayList<>());
            errors.add(fieldError.getDefaultMessage());
        });

        return ImmutableErrorResponse.builder()
                .errorCode(ErrorCode.VALIDATION_ERROR)
                .message("validation error")
                .fieldErrors(fieldErrors)
                .build();
    }

    @ExceptionHandler(value = {FieldErrorException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleFieldErrorException(FieldErrorException e, WebRequest webRequest) {
        LOG.debug("Validation error while handling request: {}", webRequest);

        Map<String, List<String>> fieldErrors = new HashMap<>();
        fieldErrors.put(e.getField(), e.getMessages());

        return ImmutableErrorResponse.builder()
                .errorCode(ErrorCode.VALIDATION_ERROR)
                .message("validation error")
                .fieldErrors(fieldErrors)
                .build();
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorResponse handleIllegalArgumentException(IllegalArgumentException e, WebRequest webRequest) {
        LOG.warn("Exception while handler request: {}", webRequest, e);
        return ImmutableErrorResponse.builder()
                .errorCode(ErrorCode.ILLEGAL_ARGUMENT)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = {CommandException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorResponse handleCommandException(CommandException e, WebRequest webRequest) {
        LOG.warn("Exception while handler request: {}", webRequest, e);
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

        LOG.warn("Exception while handler request: {}", webRequest, e);
        return new ResponseEntity<>(ImmutableErrorResponse.builder()
                .errorCode(ErrorCode.UNEXPECTED_ERROR)
                .message("an unexpected error occurred. See logs for details")
                .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
