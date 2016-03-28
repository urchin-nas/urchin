package urchin.api.support;

import java.util.List;

public class ResponseMessage<T> {

    private T data;
    private List<ErrorResponse> errors;

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
