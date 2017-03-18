package urchin.testutil;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import urchin.controller.api.support.ErrorResponse;
import urchin.controller.api.support.ResponseMessage;
import urchin.controller.api.support.error.ResponseException;

import javax.validation.Valid;

@RestController
@RequestMapping("test")
public class TestController {

    public static final String COMMUNICATION_FAILURE = "COMMUNICATION_FAILURE";

    @RequestMapping(value = "validation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<TestResponseDto>> validation(@Valid @RequestBody TestRequestDto testRequestDto) {
        TestResponseDto testResponseDto = new TestResponseDto();
        testResponseDto.setValue(testRequestDto.getValue());
        return new ResponseEntity<>(new ResponseMessage<>(testResponseDto), HttpStatus.OK);
    }

    @RequestMapping(value = "response-exception", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<TestResponseDto>> responseException() {
        throw new ResponseException(HttpStatus.SERVICE_UNAVAILABLE, new ErrorResponse(COMMUNICATION_FAILURE));
    }

    @RequestMapping(value = "runtime-exception", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<TestResponseDto>> runtimeException() {
        throw new RuntimeException("thrown exception");
    }
}
