package urchin.controller.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IdDto {

    private final int id;

    @JsonCreator
    public IdDto(@JsonProperty("id") int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
