package urchin.api.support;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import urchin.api.support.error.ResponseException;

import java.util.List;

import static java.util.Collections.singletonList;

public class ResponseEntityBuilder {

    private static final String OK = "OK";

    public static <T> ResponseEntity<ResponseMessage<T>> createResponse(T data, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ResponseMessage<>(data), httpStatus);
    }

    public static <T> ResponseEntity<ResponseMessage<T>> createResponse(T data) {
        return createResponse(data, HttpStatus.OK);
    }

    public static ResponseEntity<ResponseMessage<String>> createOkResponse() {
        return createResponse(OK, HttpStatus.OK);
    }

    public static <T> ResponseEntity<ResponseMessage<T>> createErrorResponse(ResponseException e) {
        return new ResponseEntity<>(new ResponseMessage<>(e.getErrorResponses()), e.getHttpStatus());
    }

    public static <T> ResponseEntity<ResponseMessage<T>> createErrorResponse(ErrorResponse errorResponse, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ResponseMessage<>(singletonList(errorResponse)), httpStatus);
    }

    public static <T> ResponseEntity<ResponseMessage<T>> createErrorResponse(List<ErrorResponse> errorResponses, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ResponseMessage<>(errorResponses), httpStatus);
    }
}
