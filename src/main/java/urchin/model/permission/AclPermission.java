package urchin.model.permission;

import org.immutables.value.Value;

import java.util.Arrays;

@Value.Immutable
public abstract class AclPermission {

    public static final String NONE = "-";
    public static final String READ = "r";
    public static final String WRITE = "w";
    public static final String EXECUTE = "x";

    @Value.Parameter
    public abstract String getPermissions();

    @Value.Check
    void validatePermissions() {
        if (getPermissions().length() != 3) {
            throw new IllegalArgumentException(String.format("Invalid permissions. Expected size of %d but was %d", 3, getPermissions().length()));
        }

        Arrays.stream(getPermissions().split("")).forEach(p -> {
            if (!p.equals(NONE) && !p.equals(READ) && !p.equals(WRITE) && !p.equals(EXECUTE)) {
                throw new IllegalArgumentException("Invalid permission. Expected all values to be one of -rwx");
            }
        });

    }
}
