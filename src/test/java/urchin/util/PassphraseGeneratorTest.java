package urchin.util;

import org.junit.Test;
import urchin.model.folder.Passphrase;

import static org.assertj.core.api.Assertions.assertThat;

public class PassphraseGeneratorTest {

    @Test
    public void passphraseIsGeneratedWithProperLength() {
        assertThat(PassphraseGenerator.generateEcryptfsPassphrase().getValue().length()).isEqualTo(Passphrase.ECRYPTFS_MAX_PASSPHRASE_LENGTH);
    }

}