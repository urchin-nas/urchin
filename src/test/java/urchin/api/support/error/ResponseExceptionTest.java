package urchin.api.support.error;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import urchin.api.support.ErrorResponse;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ResponseExceptionTest {

    public static final String ERROR_ONE = "ERROR_ONE";
    public static final String ERROR_TWO = "ERROR_TWO";

    @Test
    public void messageIsCreatedFromErrorResponseCode() {
        ResponseException responseException = new ResponseException(HttpStatus.BAD_REQUEST, new ErrorResponse(ERROR_ONE));

        assertEquals(ERROR_ONE, responseException.getMessage());
    }

    @Test
    public void messageIsCreatedFromErrorResponseCodes() {
        ResponseException responseException = new ResponseException(HttpStatus.BAD_REQUEST, Arrays.asList(new ErrorResponse(ERROR_ONE), new ErrorResponse(ERROR_TWO)));

        assertEquals(String.format("%s,%s", ERROR_ONE, ERROR_TWO), responseException.getMessage());
    }

}