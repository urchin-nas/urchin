package urchin.testutil;

import static org.junit.Assume.assumeFalse;

public class WindowsAssumption {

    public static void ignoreWhenWindows() {
        assumeFalse("Implementation incompatible with Windows, ignoring.", isWindows());
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").startsWith("Windows");
    }
}
