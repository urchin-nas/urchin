package urchin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import urchin.cli.common.CommandException;
import urchin.controller.api.ErrorCode;
import urchin.controller.api.ErrorDto;
import urchin.controller.api.ImmutableErrorDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {

    private final static Logger LOG = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorDto handleValidationError(MethodArgumentNotValidException e, WebRequest webRequest) {
        LOG.debug("Validation error while handling request: {}", webRequest);
        Map<String, List<String>> fieldErrors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            List<String> errors = fieldErrors.computeIfAbsent(fieldError.getField(), m -> new ArrayList<>());
            errors.add(fieldError.getDefaultMessage());
        });
        return ImmutableErrorDto.builder()
                .errorCode(ErrorCode.VALIDATION_ERROR)
                .message("validation error")
                .fieldErrors(fieldErrors)
                .build();
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorDto handleIllegalArgumentException(IllegalArgumentException e, WebRequest webRequest) {
        LOG.warn("Exception while handler request: {}", webRequest, e);
        return ImmutableErrorDto.builder()
                .errorCode(ErrorCode.ILLEGAL_ARGUMENT)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = {CommandException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorDto handleCommandException(CommandException e, WebRequest webRequest) {
        LOG.warn("Exception while handler request: {}", webRequest, e);
        return ImmutableErrorDto.builder()
                .errorCode(ErrorCode.COMMAND_EXECUTION_ERROR)
                .message(String.format("Failed to execute %s. %s", e.getCommand().getSimpleName(), e.getMessage()))
                .build();
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorDto handleUnexpectedException(Exception e, WebRequest webRequest) {
        LOG.warn("Exception while handler request: {}", webRequest, e);
        return ImmutableErrorDto.builder()
                .errorCode(ErrorCode.UNEXPECTED_ERROR)
                .message("an unexpected error occurred. See logs for details")
                .build();
    }
}
