package urchin.util;

import org.junit.Test;
import urchin.model.folder.Passphrase;

import static org.junit.Assert.assertEquals;

public class PassphraseGeneratorTest {

    @Test
    public void passphraseIsGeneratedWithProperLength() {
        assertEquals(Passphrase.ECRYPTFS_MAX_PASSPHRASE_LENGTH, PassphraseGenerator.generateEcryptfsPassphrase().getValue().length());
    }

}