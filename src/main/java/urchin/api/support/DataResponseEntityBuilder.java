package urchin.api.support;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DataResponseEntityBuilder {

    public static final String OK = "OK";

    public static <T> ResponseEntity<ResponseMessage<T>> createResponse(T data, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ResponseMessage<>(data), httpStatus);
    }

    public static <T> ResponseEntity<ResponseMessage<T>> createResponse(T data) {
        return createResponse(data, HttpStatus.OK);
    }

    public static ResponseEntity<ResponseMessage<String>> createOkResponse() {
        return createResponse(OK, HttpStatus.OK);
    }
}
