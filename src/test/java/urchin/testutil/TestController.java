package urchin.testutil;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import urchin.api.support.ControllerSupport;
import urchin.api.support.ErrorResponse;
import urchin.api.support.ResponseMessage;
import urchin.api.support.error.ResponseException;

import javax.validation.Valid;

@RestController
@RequestMapping("test")
public class TestController extends ControllerSupport {

    public static final String COMMUNICATION_FAILURE = "COMMUNICATION_FAILURE";

    @RequestMapping(value = "validation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<TestResponseApi>> validation(@Valid @RequestBody TestRequestApi testRequestApi) {
        TestResponseApi testResponseApi = new TestResponseApi();
        testResponseApi.setValue(testRequestApi.getValue());
        return new ResponseEntity<>(new ResponseMessage<>(testResponseApi), HttpStatus.OK);
    }

    @RequestMapping(value = "response-exception", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<TestResponseApi>> responseException() {
        throw new ResponseException(HttpStatus.SERVICE_UNAVAILABLE, new ErrorResponse(COMMUNICATION_FAILURE));
    }

    @RequestMapping(value = "runtime-exception", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<TestResponseApi>> runtimeException() {
        throw new RuntimeException("thrown exception");
    }
}
