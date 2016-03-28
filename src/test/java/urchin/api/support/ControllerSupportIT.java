package urchin.api.support;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import urchin.testutil.SpringApplication;
import urchin.testutil.TestController;
import urchin.testutil.TestRequestApi;
import urchin.testutil.TestResponseApi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
public class ControllerSupportIT extends SpringApplication {

    public static final String VALIDATION = "/validation";

    @Override
    protected String getPath() {
        return "/test";
    }

    @Test
    public void validationFailureReturnsErrorResponse() {
        ResponseEntity<ResponseMessage<TestResponseApi>> response = postRequest(url + VALIDATION, new TestRequestApi());

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
        TestRequestApi requestApi = new TestRequestApi();
        requestApi.setValue("someValue");
        requestApi.setValueWithoutCustomValidationMessage("someOtherValue");
        ResponseEntity<ResponseMessage<TestResponseApi>> response = postRequest(url + VALIDATION, requestApi);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody().getErrors());
        assertEquals(requestApi.getValue(), response.getBody().getData().getValue());
    }

    @Test
    public void thrownResponseExceptionResultsInErrorResponse() {
        ResponseEntity<ResponseMessage<TestResponseApi>> response = getRequest(url + "/response-exception");
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertNull(response.getBody().getData());
        assertEquals(1, response.getBody().getErrors().size());
        ErrorResponse errorResponse = response.getBody().getErrors().get(0);
        assertEquals(TestController.COMMUNICATION_FAILURE, errorResponse.getCode());
    }

    @Test
    public void thrownRuntimeExceptionResultsInErrorResponse() {
        ResponseEntity<ResponseMessage<TestResponseApi>> response = getRequest(url + "/runtime-exception");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody().getData());
        assertEquals(1, response.getBody().getErrors().size());
        ErrorResponse errorResponse = response.getBody().getErrors().get(0);
        assertEquals(ExceptionControllerAdvice.UNKNOWN_ERROR, errorResponse.getCode());
    }

    private ResponseEntity<ResponseMessage<TestResponseApi>> postRequest(String url, TestRequestApi requestApi) {
        return template.exchange(url, HttpMethod.POST, new HttpEntity<>(requestApi), new ParameterizedTypeReference<ResponseMessage<TestResponseApi>>() {
        });
    }

    private ResponseEntity<ResponseMessage<TestResponseApi>> getRequest(String url) {
        return template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<ResponseMessage<TestResponseApi>>() {
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