package urchin.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import urchin.domain.Passphrase;

public class PassphraseGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(PassphraseGenerator.class);

    public static final int ECRYPTFS_MAX_PASSPHRASE_LENGTH = 64;

    public static Passphrase generateEcryptfsPassphrase() {
        LOG.debug("Generating passphrase for ecryptfs");
        return new Passphrase(RandomStringUtils.randomAlphanumeric(ECRYPTFS_MAX_PASSPHRASE_LENGTH));
    }
}
