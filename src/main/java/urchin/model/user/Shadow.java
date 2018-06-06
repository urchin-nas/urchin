package urchin.model.user;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Shadow {

    static final String SHA_512 = "SHA-512";
    static final String SHA_256 = "SHA-256";
    static final String MD5 = "MD5";

    public abstract String getId();

    public abstract String getSalt();

    public abstract String getEncryptedPassword();

    public String getEncryptionMethod() {
        switch (getId()) {
            case "6":
                return SHA_512;
            case "5":
                return SHA_256;
            case "1":
                return MD5;
            default:
                throw new IllegalArgumentException("Unsupported encryption method id " + getId());
        }
    }
}
