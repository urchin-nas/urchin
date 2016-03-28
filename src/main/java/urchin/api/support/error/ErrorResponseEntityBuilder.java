package urchin.api.support.error;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import urchin.api.support.ErrorResponse;
import urchin.api.support.ResponseMessage;

import java.util.List;

import static java.util.Collections.singletonList;

public class ErrorResponseEntityBuilder {

    public static ResponseEntity<ResponseMessage> createResponseEntity(ResponseException e) {
        return new ResponseEntity<>(new ResponseMessage<>(e.getErrorResponses()), e.getHttpStatus());
    }

    public static ResponseEntity<ResponseMessage> createResponseEntity(ErrorResponse errorResponse, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ResponseMessage<>(singletonList(errorResponse)), httpStatus);
    }

    public static ResponseEntity<ResponseMessage> createResponseEntity(List<ErrorResponse> errorResponses, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ResponseMessage<>(errorResponses), httpStatus);
    }
}
