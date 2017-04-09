package urchin.controller.api.support;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class ResponseMessage<T> {

    private T data;
    private List<ErrorResponse> errors;

    @JsonCreator
    private ResponseMessage() {
    }

    public ResponseMessage(T data) {
        this.data = data;
    }

    public ResponseMessage(List<ErrorResponse> errors) {
        this.errors = errors;
    }

    public T getData() {
        return data;
    }

    public List<ErrorResponse> getErrors() {
        return errors;
    }
}