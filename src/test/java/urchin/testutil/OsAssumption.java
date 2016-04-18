package urchin.testutil;

import static org.junit.Assume.assumeFalse;

public class OsAssumption {

    public static void ignoreWhenWindowsOrMac() {
        assumeFalse("Implementation incompatible with current OS, ignoring.", isWindows() || isMac());
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").startsWith("Windows");
    }

    private static boolean isMac() {
        return System.getProperty("os.name").startsWith("Mac");
    }
}
