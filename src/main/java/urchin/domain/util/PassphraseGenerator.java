package urchin.domain.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import urchin.domain.model.ImmutablePassphrase;
import urchin.domain.model.Passphrase;

import static urchin.domain.model.Passphrase.ECRYPTFS_MAX_PASSPHRASE_LENGTH;

public class PassphraseGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(PassphraseGenerator.class);

    public static Passphrase generateEcryptfsPassphrase() {
        LOG.info("Generating passphrase for ecryptfs");
        return ImmutablePassphrase.of(RandomStringUtils.randomAlphanumeric(ECRYPTFS_MAX_PASSPHRASE_LENGTH));
    }
}
