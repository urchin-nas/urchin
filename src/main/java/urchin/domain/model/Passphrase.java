package urchin.domain.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Passphrase {

    public static final int ECRYPTFS_MAX_PASSPHRASE_LENGTH = 64;

    private final String passphrase;

    public Passphrase(String passphrase) {
        if (passphrase.length() < ECRYPTFS_MAX_PASSPHRASE_LENGTH) {
            throw new IllegalArgumentException("passphrase not long enough");
        }
        this.passphrase = passphrase;
    }

    public String getPassphrase() {
        return passphrase;
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
