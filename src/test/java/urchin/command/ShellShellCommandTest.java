package urchin.command;

import org.junit.Before;
import org.junit.Test;

public class ShellShellCommandTest {

    private ShellCommand shellCommand;

    @Before
    public void setup() {
        shellCommand = new ShellCommand(Runtime.getRuntime());
    }


}