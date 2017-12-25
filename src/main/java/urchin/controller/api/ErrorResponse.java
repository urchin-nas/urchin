package urchin.controller.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.List;
import java.util.Map;

@Value.Immutable
@Value.Style(jdkOnly = true)
@JsonSerialize(as = ImmutableErrorResponse.class)
@JsonDeserialize(as = ImmutableErrorResponse.class)
public interface ErrorResponse {

    ErrorCode getErrorCode();

    String getMessage();

    Map<String, List<String>> getFieldErrors();
}
