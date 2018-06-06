package urchin.model.user;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ShadowTest {

    @Test
    public void correctEncryptionMethodIsReturned() {
        Shadow shadow = ImmutableShadow.builder()
                .id("5")
                .salt("salt")
                .encryptedPassword("encryptedPassword")
                .build();

        assertThat(shadow.getEncryptionMethod()).isEqualTo(Shadow.SHA_256);

        shadow = ImmutableShadow.builder()
                .id("6")
                .salt("salt")
                .encryptedPassword("encryptedPassword")
                .build();

        assertThat(shadow.getEncryptionMethod()).isEqualTo(Shadow.SHA_512);

        shadow = ImmutableShadow.builder()
                .id("1")
                .salt("salt")
                .encryptedPassword("encryptedPassword")
                .build();

        assertThat(shadow.getEncryptionMethod()).isEqualTo(Shadow.MD5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionIsThrownWhenIdIsNotRecognized() {
        Shadow shadow = ImmutableShadow.builder()
                .id("0")
                .salt("salt")
                .encryptedPassword("encryptedPassword")
                .build();

        shadow.getEncryptionMethod();
    }

}