package urchin.model.folder;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Passphrase {

    public static final int ECRYPTFS_MAX_PASSPHRASE_LENGTH = 64;

    @Value.Parameter
    public abstract String getValue();

    @Value.Check
    void validateLenght() {
        if (getValue().length() < ECRYPTFS_MAX_PASSPHRASE_LENGTH) {
            throw new IllegalArgumentException(String.format("passphrase must be at least %d characters but was %d", ECRYPTFS_MAX_PASSPHRASE_LENGTH, getValue().length()));
        }
    }
}
