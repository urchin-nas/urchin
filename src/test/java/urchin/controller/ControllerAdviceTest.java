package urchin.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import urchin.cli.BasicCommand;
import urchin.controller.api.ErrorResponse;
import urchin.exception.CommandException;
import urchin.exception.FieldErrorException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;
import static urchin.controller.ControllerAdvice.UNEXPECTED_ERROR_MESSAGE;
import static urchin.controller.ControllerAdvice.VALIDATION_ERROR_MESSAGE;
import static urchin.controller.api.ErrorCode.*;

@RunWith(MockitoJUnitRunner.class)
public class ControllerAdviceTest {

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;
    @Mock
    private WebRequest webRequest;
    @Mock
    private BindingResult bindingResult;

    private ControllerAdvice controllerAdvice;
    private String usernameField = "username";
    private String passwordField = "password";
    private String usernameErrorMessage_1 = "username error message 1";
    private String usernameErrorMessage_2 = "username error message 2";
    private String passwordErrorMessage = "password error message 1";

    @Before
    public void setUp() {
        controllerAdvice = new ControllerAdvice();
    }

    @Test
    public void validationErrorsAreMappedToErrorResponse() {
        FieldError fieldError_1 = new FieldError("", usernameField, usernameErrorMessage_1);
        FieldError fieldError_2 = new FieldError("", passwordField, passwordErrorMessage);
        FieldError fieldError_3 = new FieldError("", usernameField, usernameErrorMessage_2);
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(fieldError_1, fieldError_2, fieldError_3));

        ErrorResponse errorResponse = controllerAdvice.handleValidationError(methodArgumentNotValidException, webRequest);

        assertThat(errorResponse.getErrorCode()).isEqualTo(VALIDATION_ERROR);
        assertThat(errorResponse.getMessage()).isEqualTo(VALIDATION_ERROR_MESSAGE);
        assertThat(errorResponse.getFieldErrors()).hasSize(2);

        List<String> usernameErrors = errorResponse.getFieldErrors().get(usernameField);
        List<String> passwordErrors = errorResponse.getFieldErrors().get(passwordField);

        assertThat(usernameErrors).hasSize(2);
        assertThat(usernameErrors.get(0)).isEqualTo(usernameErrorMessage_1);
        assertThat(usernameErrors.get(1)).isEqualTo(usernameErrorMessage_2);

        assertThat(passwordErrors).hasSize(1);
        assertThat(passwordErrors.get(0)).isEqualTo(passwordErrorMessage);
    }

    @Test
    public void FieldErrorExceptionIsMappedToErrorResponse() {
        FieldErrorException fieldErrorException = new FieldErrorException(usernameField, Arrays.asList(usernameErrorMessage_1, usernameErrorMessage_2));

        ErrorResponse errorResponse = controllerAdvice.handleFieldErrorException(fieldErrorException, webRequest);

        assertThat(errorResponse.getErrorCode()).isEqualTo(VALIDATION_ERROR);
        assertThat(errorResponse.getMessage()).isEqualTo(VALIDATION_ERROR_MESSAGE);
        assertThat(errorResponse.getFieldErrors()).hasSize(1);

        List<String> usernameErrors = errorResponse.getFieldErrors().get(usernameField);

        assertThat(usernameErrors).hasSize(2);
        assertThat(usernameErrors.get(0)).isEqualTo(usernameErrorMessage_1);
        assertThat(usernameErrors.get(1)).isEqualTo(usernameErrorMessage_2);
    }

    @Test
    public void illegalArgumentExceptionIsMappedToErrorResponse() {
        ErrorResponse errorResponse = controllerAdvice.handleIllegalArgumentException(new IllegalArgumentException(passwordErrorMessage), webRequest);

        assertThat(errorResponse.getErrorCode()).isEqualTo(ILLEGAL_ARGUMENT);
        assertThat(errorResponse.getMessage()).isEqualTo(passwordErrorMessage);
        assertThat(errorResponse.getFieldErrors()).hasSize(0);
    }

    @Test
    public void illegalStateExceptionIsMappedToErrorResponse() {
        ErrorResponse errorResponse = controllerAdvice.handleIllegalstateException(new IllegalStateException(passwordErrorMessage), webRequest);

        assertThat(errorResponse.getErrorCode()).isEqualTo(ILLEGAL_STATE);
        assertThat(errorResponse.getMessage()).isEqualTo(passwordErrorMessage);
        assertThat(errorResponse.getFieldErrors()).hasSize(0);
    }

    @Test
    public void commandExceptionIsMappedToErrorResponse() {
        ErrorResponse errorResponse = controllerAdvice.handleCommandException(new CommandException(BasicCommand.class, -1), webRequest);

        assertThat(errorResponse.getErrorCode()).isEqualTo(COMMAND_EXECUTION_ERROR);
        assertThat(errorResponse.getMessage()).contains(BasicCommand.class.getSimpleName());
        assertThat(errorResponse.getMessage()).contains("-1");
        assertThat(errorResponse.getFieldErrors()).hasSize(0);
    }

    @Test
    public void exceptionIsMappedToResponseEntityWithErrorResponse() {
        ResponseEntity<ErrorResponse> responseEntity = controllerAdvice.handleUnexpectedException(new RuntimeException(passwordErrorMessage), webRequest);

        ErrorResponse errorResponse = responseEntity.getBody();
        assertThat(errorResponse.getErrorCode()).isEqualTo(UNEXPECTED_ERROR);
        assertThat(errorResponse.getMessage()).isEqualTo(UNEXPECTED_ERROR_MESSAGE);
        assertThat(errorResponse.getFieldErrors()).hasSize(0);
    }

    @Test
    public void exceptionIsMappedToResponseEntityWithErrorResponseForValidationErrorWhenFieldErrorExceptionWasTheRootCause() {
        RuntimeException e = new RuntimeException(new FieldErrorException(passwordField, passwordErrorMessage));
        ResponseEntity<ErrorResponse> responseEntity = controllerAdvice.handleUnexpectedException(e, webRequest);

        ErrorResponse errorResponse = responseEntity.getBody();
        assertThat(errorResponse.getErrorCode()).isEqualTo(VALIDATION_ERROR);
        assertThat(errorResponse.getMessage()).isEqualTo(VALIDATION_ERROR_MESSAGE);
        assertThat(errorResponse.getFieldErrors()).hasSize(1);

        List<String> usernameErrors = errorResponse.getFieldErrors().get(passwordField);

        assertThat(usernameErrors).hasSize(1);
        assertThat(usernameErrors.get(0)).isEqualTo(passwordErrorMessage);
    }

}