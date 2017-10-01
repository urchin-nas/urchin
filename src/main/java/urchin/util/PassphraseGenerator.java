package urchin.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import urchin.model.folder.ImmutablePassphrase;
import urchin.model.folder.Passphrase;

import static urchin.model.folder.Passphrase.ECRYPTFS_MAX_PASSPHRASE_LENGTH;

public class PassphraseGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(PassphraseGenerator.class);

    public static Passphrase generateEcryptfsPassphrase() {
        LOG.info("Generating passphrase for ecryptfs");
        return ImmutablePassphrase.of(RandomStringUtils.randomAlphanumeric(ECRYPTFS_MAX_PASSPHRASE_LENGTH));
    }
}
