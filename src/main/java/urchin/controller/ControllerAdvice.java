package urchin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import urchin.controller.api.ErrorCode;
import urchin.controller.api.ErrorDto;
import urchin.controller.api.FieldErrorDto;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    static final String VALIDATION_MESSAGE_DELIMITER = ";";
    private final static Logger LOG = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorDto handleValidationError(MethodArgumentNotValidException e, WebRequest webRequest) {
        List<FieldErrorDto> fieldErrorDtos = e.getBindingResult().getFieldErrors().stream()
                .map(this::toFieldErrorDto)
                .collect(Collectors.toList());
        LOG.debug("Validation error while handling request: {}", webRequest);
        return new ErrorDto(ErrorCode.VALIDATION_ERROR, "validation error", fieldErrorDtos);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorDto handleException(Exception e, WebRequest webRequest) {
        LOG.warn("Exception while handler request: {}", webRequest, e);
        return new ErrorDto(ErrorCode.UNEXPECTED_ERROR, "an unexpected error occurred. See logs for details");
    }

    private FieldErrorDto toFieldErrorDto(FieldError fieldError) {
        String code;
        String message;
        if (fieldError.getDefaultMessage().contains(VALIDATION_MESSAGE_DELIMITER)) {
            String[] pair = fieldError.getDefaultMessage().split(VALIDATION_MESSAGE_DELIMITER);
            code = pair[0];
            message = pair[1];
        } else {
            code = fieldError.getDefaultMessage();
            message = fieldError.getDefaultMessage();
        }
        return new FieldErrorDto(code, fieldError.getField(), message);
    }
}
