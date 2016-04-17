package urchin.testutil;

public class WindowsAssumption {

    public static boolean isWindows() {
        return System.getProperty("os.name").startsWith("Windows");
    }
}
