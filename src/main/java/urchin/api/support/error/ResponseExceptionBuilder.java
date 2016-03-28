package urchin.api.support.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import urchin.api.support.ErrorResponse;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class ResponseExceptionBuilder {

    public static final String SEE_LOG_MESSAGE = "see logs for more details";
    public static final String UNEXPECTED_ERROR = "UNEXPECTED_ERROR";
    private final static Logger LOG = LoggerFactory.getLogger(ResponseExceptionBuilder.class);

    public static ResponseException unexpectedError(Exception e) {
        LOG.error("Unexpected error", e);
        return new ResponseException(INTERNAL_SERVER_ERROR, new ErrorResponse(UNEXPECTED_ERROR).setMessage(SEE_LOG_MESSAGE));
    }
}
