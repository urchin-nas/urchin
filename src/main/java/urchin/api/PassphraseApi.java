package urchin.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PassphraseApi {

    private final String passphrase;

    @JsonCreator
    public PassphraseApi(@JsonProperty("passphrase") String passphrase) {
        this.passphrase = passphrase;
    }

    public String getPassphrase() {
        return passphrase;
    }
}
