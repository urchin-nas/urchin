package urchin.model.permission;

import org.immutables.value.Value;

import java.util.Arrays;

@Value.Immutable
public abstract class AclPermission {

    @Value.Parameter
    public abstract String getPermissions();

    @Value.Check
    void validatePermissions() {
        if (getPermissions().length() != 3) {
            throw new IllegalArgumentException(String.format("Invalid permissions. Expected size of %d but was %d", 3, getPermissions().length()));
        }

        Arrays.stream(getPermissions().split("")).forEach(p -> {
            if (!p.equals("-") && !p.equals("r") && !p.equals("w") && !p.equals("x")) {
                throw new IllegalArgumentException("Invalid permission. Expected all values to be one of -rwx");
            }
        });

    }
}
