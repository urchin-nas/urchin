package urchin.util;

import org.apache.commons.lang3.RandomStringUtils;
import urchin.domain.Passphrase;

public class PassphraseGenerator {

    public static final int ECRYPTFS_MAX_PASSPHRASE_LENGTH = 64;

    public static Passphrase generateEcryptfsPassphrase() {
        return new Passphrase(RandomStringUtils.randomAlphanumeric(ECRYPTFS_MAX_PASSPHRASE_LENGTH));
    }
}
