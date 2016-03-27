package urchin.api.support;

import java.util.List;

public class ResponseMessage<T> {

    private T data;
    private List<ErrorMessage> error;

    public ResponseMessage(T data) {
        this.data = data;
    }

    public ResponseMessage(List<ErrorMessage> error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public List<ErrorMessage> getError() {
        return error;
    }
}
