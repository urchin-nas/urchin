package urchin.model;

import org.junit.Test;
import urchin.model.permission.FileModes;
import urchin.model.permission.ImmutableFileModes;

import static org.assertj.core.api.Assertions.assertThat;

public class FileModesTest {

    private static final String MODES = "123";

    @Test
    public void getModesReturnStringOfModes() {
        FileModes fileModes = ImmutableFileModes.builder()
                .owner(1)
                .group(2)
                .other(3)
                .build();

        assertThat(fileModes.getModes()).isEqualTo(MODES);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidOwnerFileModeThrowsException() {
        ImmutableFileModes.builder()
                .owner(-1)
                .group(2)
                .other(3)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidGroupFileModeThrowsException() {
        ImmutableFileModes.builder()
                .owner(1)
                .group(8)
                .other(3)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidOtherFileModeThrowsException() {
        ImmutableFileModes.builder()
                .owner(1)
                .group(2)
                .other(8)
                .build();
    }

    @Test
    public void fileModesAreCreatedFromString() {
        FileModes fileModes = ImmutableFileModes.from("-rw-rw-rw-");

        assertThat(fileModes.getModes()).isEqualTo("666");

        fileModes = ImmutableFileModes.from("----rwxr-x");

        assertThat(fileModes.getModes()).isEqualTo("075");

        fileModes = ImmutableFileModes.from("-rwxrwxrwx");

        assertThat(fileModes.getModes()).isEqualTo("777");

        fileModes = ImmutableFileModes.from("-r--r--rw-");

        assertThat(fileModes.getModes()).isEqualTo("446");

        fileModes = ImmutableFileModes.from("dr--r--rw-");

        assertThat(fileModes.getModes()).isEqualTo("446");

        fileModes = ImmutableFileModes.from("----------");

        assertThat(fileModes.getModes()).isEqualTo("000");

    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidModeThrowsException() {
        ImmutableFileModes.from("---a------");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidLengthOfStringThrowsException() {
        ImmutableFileModes.from("--");
    }

}