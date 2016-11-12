package urchin.api.support;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import urchin.api.support.error.ExceptionControllerAdvice;
import urchin.testutil.TestApplication;
import urchin.testutil.TestController;
import urchin.testutil.TestRequestDto;
import urchin.testutil.TestResponseDto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ControllerSupportIT extends TestApplication {

    private static final String VALIDATION = "/validation";

    @Override
    protected String discoverControllerPath() {
        return "/test";
    }

    @Test
    public void validationFailureReturnsErrorResponse() {
        ResponseEntity<ResponseMessage<TestResponseDto>> response = postRequest(discoverControllerPath() + VALIDATION, new TestRequestDto());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody().getData());
        assertEquals(2, response.getBody().getErrors().size());
        ErrorResponse errorResponse_1 = response.getBody().getErrors().get(0);
        ErrorResponse errorResponse_2 = response.getBody().getErrors().get(1);
        if (errorResponse_1.getField().equals("value")) {
            assertValidationErrorForValue(errorResponse_1);
            assertValidationErrorForValueWithoutCustomValidationMessage(errorResponse_2);
        } else {
            assertValidationErrorForValue(errorResponse_2);
            assertValidationErrorForValueWithoutCustomValidationMessage(errorResponse_1);
        }
    }

    @Test
    public void successfulValidationRequestReturnsResponseMessage() {
        TestRequestDto requestApi = new TestRequestDto();
        requestApi.setValue("someValue");
        requestApi.setValueWithoutCustomValidationMessage("someOtherValue");
        ResponseEntity<ResponseMessage<TestResponseDto>> response = postRequest(discoverControllerPath() + VALIDATION, requestApi);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody().getErrors());
        assertEquals(requestApi.getValue(), response.getBody().getData().getValue());
    }

    @Test
    public void thrownResponseExceptionResultsInErrorResponse() {
        ResponseEntity<ResponseMessage<TestResponseDto>> response = getRequest(discoverControllerPath() + "/response-exception");
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertNull(response.getBody().getData());
        assertEquals(1, response.getBody().getErrors().size());
        ErrorResponse errorResponse = response.getBody().getErrors().get(0);
        assertEquals(TestController.COMMUNICATION_FAILURE, errorResponse.getCode());
    }

    @Test
    public void thrownRuntimeExceptionResultsInErrorResponse() {
        ResponseEntity<ResponseMessage<TestResponseDto>> response = getRequest(discoverControllerPath() + "/runtime-exception");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody().getData());
        assertEquals(1, response.getBody().getErrors().size());
        ErrorResponse errorResponse = response.getBody().getErrors().get(0);
        assertEquals(ExceptionControllerAdvice.UNKNOWN_ERROR, errorResponse.getCode());
    }

    private ResponseEntity<ResponseMessage<TestResponseDto>> postRequest(String url, TestRequestDto requestApi) {
        return testRestTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(requestApi), new ParameterizedTypeReference<ResponseMessage<TestResponseDto>>() {
        });
    }

    private ResponseEntity<ResponseMessage<TestResponseDto>> getRequest(String url) {
        return testRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<ResponseMessage<TestResponseDto>>() {
        });
    }

    private void assertValidationErrorForValueWithoutCustomValidationMessage(ErrorResponse errorResponse) {
        assertEquals("may not be null", errorResponse.getCode());
        assertEquals("valueWithoutCustomValidationMessage", errorResponse.getField());
        assertEquals("may not be null", errorResponse.getMessage());
    }

    private void assertValidationErrorForValue(ErrorResponse errorResponse) {
        assertEquals("FIELD_MISSING", errorResponse.getCode());
        assertEquals("value", errorResponse.getField());
        assertEquals("Required field is missing", errorResponse.getMessage());
    }

}