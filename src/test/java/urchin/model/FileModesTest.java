package urchin.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileModesTest {

    private static final String MODES = "123";

    @Test
    public void getModesReturnStringOfModes() {
        FileModes fileModes = ImmutableFileModes.builder()
                .owner(1)
                .group(2)
                .other(3)
                .build();

        assertEquals(MODES, fileModes.getModes());
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

        assertEquals("666", fileModes.getModes());

        fileModes = ImmutableFileModes.from("----rwxr-x");

        assertEquals("075", fileModes.getModes());

        fileModes = ImmutableFileModes.from("-rwxrwxrwx");

        assertEquals("777", fileModes.getModes());

        fileModes = ImmutableFileModes.from("-r--r--rw-");

        assertEquals("446", fileModes.getModes());

        fileModes = ImmutableFileModes.from("dr--r--rw-");

        assertEquals("446", fileModes.getModes());

        fileModes = ImmutableFileModes.from("----------");

        assertEquals("000", fileModes.getModes());

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