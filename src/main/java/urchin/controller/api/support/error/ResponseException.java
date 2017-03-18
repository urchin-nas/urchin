package urchin.controller.api.support.error;


import org.springframework.http.HttpStatus;
import urchin.controller.api.support.ErrorResponse;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;
import static org.apache.commons.lang3.StringUtils.join;

public class ResponseException extends RuntimeException {

    private static final String CODE_DELIMITER = ",";

    private final HttpStatus httpStatus;
    private List<ErrorResponse> errorResponses;

    public ResponseException(HttpStatus httpStatus, ErrorResponse errorResponse) {
        super(errorResponse.getCode());
        this.httpStatus = httpStatus;
        this.errorResponses = singletonList(errorResponse);
    }

    public ResponseException(HttpStatus httpStatus, ErrorResponse errorResponse, Throwable cause) {
        super(errorResponse.getCode(), cause);
        this.httpStatus = httpStatus;
        this.errorResponses = singletonList(errorResponse);
    }

    public ResponseException(HttpStatus httpStatus, List<ErrorResponse> errorResponses) {
        super(getCodes(errorResponses));
        this.httpStatus = httpStatus;
        this.errorResponses = errorResponses;
    }

    public ResponseException(HttpStatus httpStatus, List<ErrorResponse> errorResponses, Throwable cause) {
        super(getCodes(errorResponses), cause);
        this.httpStatus = httpStatus;
        this.errorResponses = errorResponses;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public List<ErrorResponse> getErrorResponses() {
        return errorResponses;
    }

    private static String getCodes(List<ErrorResponse> errorResponses) {
        List<String> codes = errorResponses.stream().map(ErrorResponse::getCode).collect(Collectors.toList());
        return join(codes, CODE_DELIMITER);
    }
}
