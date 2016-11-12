package urchin.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PassphraseDto {

    private final String passphrase;

    @JsonCreator
    public PassphraseDto(@JsonProperty("passphrase") String passphrase) {
        this.passphrase = passphrase;
    }

    public String getPassphrase() {
        return passphrase;
    }
}
