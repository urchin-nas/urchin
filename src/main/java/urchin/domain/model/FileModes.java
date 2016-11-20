package urchin.domain.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
public class FileModes {

    private final int owner;
    private final int group;
    private final int other;

    public FileModes(int owner, int group, int other) {
        this.owner = owner;
        this.group = group;
        this.other = other;
        validateModes();
    }

    public FileModes(String modes) {
        if (modes.length() != 10) {
            throw new IllegalArgumentException("Invalid file modes. Expected string to be of length 10 but was " + modes.length());
        }
        this.owner = parseAbsoluteNotation(modes.substring(1, 4));
        this.group = parseAbsoluteNotation(modes.substring(4, 7));
        this.other = parseAbsoluteNotation(modes.substring(7, 10));
        validateModes();
    }

    private int parseAbsoluteNotation(String fileModes) {
        int absoluteNotation = 0;
        for (String fileMode : fileModes.split("")) {
            absoluteNotation += getAbsoluteNotationValue(fileMode);
        }
        return absoluteNotation;
    }

    private int getAbsoluteNotationValue(String fileMode) {
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

    private void validateModes() {
        validateMode(owner);
        validateMode(group);
        validateMode(other);
    }

    private void validateMode(int mode) {
        if (0 > mode || mode > 7) {
            throw new IllegalArgumentException(format("Invalid file mode %d. Must be between 0 and 7", mode));
        }
    }

    public String getModes() {
        return format("%d%d%d", owner, group, other);
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
