package urchin.domain.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileModesTest {

    private static final String MODES = "123";

    @Test
    public void getModesReturnStringOfModes() {
        FileModes fileModes = new FileModes(1, 2, 3);

        assertEquals(MODES, fileModes.getModes());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidOwnerFileModeThrowsException() {
        new FileModes(-1, 2, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidGroupFileModeThrowsException() {
        new FileModes(1, 8, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidOtherFileModeThrowsException() {
        new FileModes(1, 2, 8);
    }

    @Test
    public void fileModesAreCreatedFromString() {
        FileModes fileModes = new FileModes("-rw-rw-rw-");

        assertEquals("666", fileModes.getModes());

        fileModes = new FileModes("----rwxr-x");

        assertEquals("075", fileModes.getModes());

        fileModes = new FileModes("-rwxrwxrwx");

        assertEquals("777", fileModes.getModes());

        fileModes = new FileModes("-r--r--rw-");

        assertEquals("446", fileModes.getModes());

        fileModes = new FileModes("dr--r--rw-");

        assertEquals("446", fileModes.getModes());

        fileModes = new FileModes("----------");

        assertEquals("000", fileModes.getModes());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidModeThrowsException() {
        new FileModes("---a------");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidLengthOfStringThrowsException() {
        new FileModes("--");
    }

}