package urchin.model.permission;

import org.immutables.value.Value;

import static java.lang.String.format;

/**
 * 0    No permission	---
 * 1	Execute permission	--x
 * 2	Write permission	-w-
 * 3	Execute and write permission: 1 (execute) + 2 (write) = 3	-wx
 * 4	Read permission	r--
 * 5	Read and execute permission: 4 (read) + 1 (execute) = 5	r-x
 * 6	Read and write permission: 4 (read) + 2 (write) = 6	rw-
 * 7	All permissions: 4 (read) + 2 (write) + 1 (execute) = 7	rwx
 */
@Value.Immutable
public abstract class FileModes {

    public abstract int getOwner();

    public abstract int getGroup();

    public abstract int getOther();

    public String getModes() {
        return format("%d%d%d", getOwner(), getGroup(), getOther());
    }

    public static FileModes from(String modes) {
        if (modes.length() != 10) {
            throw new IllegalArgumentException("Invalid file modes. Expected string to be of length 10 but was " + modes.length());
        }
        return ImmutableFileModes.builder()
                .owner(parseAbsoluteNotation(modes.substring(1, 4)))
                .group(parseAbsoluteNotation(modes.substring(4, 7)))
                .other(parseAbsoluteNotation(modes.substring(7, 10)))
                .build();
    }

    private static int parseAbsoluteNotation(String fileModes) {
        int absoluteNotation = 0;
        for (String fileMode : fileModes.split("")) {
            absoluteNotation += getAbsoluteNotationValue(fileMode);
        }
        return absoluteNotation;
    }

    private static int getAbsoluteNotationValue(String fileMode) {
        switch (fileMode) {
            case "-":
                return 0;
            case "x":
                return 1;
            case "w":
                return 2;
            case "r":
                return 4;
            default:
                throw new IllegalArgumentException(format("Invalid file mode %s provided", fileMode));
        }
    }

    @Value.Check
    void validateModes() {
        validateMode(getOwner());
        validateMode(getGroup());
        validateMode(getOther());
    }

    private void validateMode(int mode) {
        if (0 > mode || mode > 7) {
            throw new IllegalArgumentException(format("Invalid file mode %d. Must be between 0 and 7", mode));
        }
    }
}
