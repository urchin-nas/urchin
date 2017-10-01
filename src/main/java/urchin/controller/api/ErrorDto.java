package urchin.controller.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.List;
import java.util.Map;

@Value.Immutable
@JsonSerialize(as = ImmutableErrorDto.class)
@JsonDeserialize(as = ImmutableErrorDto.class)
public interface ErrorDto {

    ErrorCode getErrorCode();

    String getMessage();

    Map<String, List<String>> getFieldErrors();
}
