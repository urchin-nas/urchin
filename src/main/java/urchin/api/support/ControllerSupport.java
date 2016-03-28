package urchin.api.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import urchin.api.support.error.ResponseException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static urchin.api.support.error.ErrorResponseEntityBuilder.createResponseEntity;

public abstract class ControllerSupport {

    public static final String VALIDATION_MESSAGE_DELIMITER = ";";
    public static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";
    private final static Logger LOG = LoggerFactory.getLogger(ControllerSupport.class);

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<ResponseMessage> handleValidationError(MethodArgumentNotValidException e, WebRequest webRequest) {
        List<ErrorResponse> errorResponses = e.getBindingResult().getFieldErrors().stream().map(this::createErrorResponse).collect(Collectors.toList());
        LOG.debug("Validation error while handling request: {}", webRequest);
        return createResponseEntity(errorResponses, BAD_REQUEST);
    }

    @ExceptionHandler(value = {ResponseException.class})
    protected ResponseEntity<ResponseMessage> handleResponseException(ResponseException e, WebRequest webRequest) {
        LOG.warn("ResponseException while handling request: {}", webRequest, e);
        return createResponseEntity(e);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ResponseMessage> handleException(Exception e, WebRequest webRequest) {
        LOG.warn("Exception while handler request: {}", webRequest, e);
        ErrorResponse errorResponse = new ErrorResponse(UNKNOWN_ERROR);
        return createResponseEntity(errorResponse, INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse createErrorResponse(FieldError fieldError) {
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
        return new ErrorResponse(code).setMessage(message).setField(fieldError.getField());
    }

}
