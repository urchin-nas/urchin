package urchin.domain.cli.common;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static java.nio.charset.Charset.defaultCharset;
import static org.apache.commons.io.IOUtils.toInputStream;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BasicCommandTest {

    private static final String[] COMMAND = new String[]{"some", "command"};
    private static final String SOME_RESPONSE = "some response";

    @Mock
    private Runtime runtime;

    @Mock
    private Process process;

    private TestCommand testCommand;

    @Before
    public void setup() throws IOException {
        testCommand = new TestCommand(runtime);

        when(runtime.exec(COMMAND)).thenReturn(process);
    }

    @Test
    public void responseIsReturnedOnSuccessfulCommandExecution() {
        InputStream inputStream = toInputStream(SOME_RESPONSE, defaultCharset());
        when(process.getInputStream()).thenReturn(inputStream);

        Optional<String> stringOptional = testCommand.executeCommand(COMMAND);

        assertTrue(stringOptional.isPresent());
        assertEquals(SOME_RESPONSE, stringOptional.get());
    }

    @Test
    public void noResponseIsReturnedOnSuccessfulCommandExecutionIfCommandReturnsNothing() throws IOException {
        InputStream emptyInputStream = toInputStream("", defaultCharset());
        when(process.getInputStream()).thenReturn(emptyInputStream);

        Optional<String> stringOptional = testCommand.executeCommand(COMMAND);

        assertFalse(stringOptional.isPresent());
    }

    @Test
    public void exceptionIsThrownWhenCommandDoesNotReturnExitValueZero() {
        int exitValue = -1;
        when(process.exitValue()).thenReturn(exitValue);

        try {
            testCommand.executeCommand(COMMAND);
            fail("Expected CommandException");
        } catch (CommandException e) {
            assertEquals(exitValue, e.getExitValue().intValue());
            assertEquals(TestCommand.class.getCanonicalName(), e.getCommandName());
            assertEquals(CommandException.PROCESS_RETURNED_EXIT_VALUE + exitValue, e.getMessage());
        }
    }

    @Test
    public void exceptionIsThrownWhenCommandResultsInException() {
        RuntimeException runtimeException = new RuntimeException("some failure");
        when(process.exitValue()).thenThrow(runtimeException);

        try {
            testCommand.executeCommand(COMMAND);
            fail("Expected CommandException");
        } catch (CommandException e) {
            assertNull(e.getExitValue());
            assertEquals(TestCommand.class.getCanonicalName(), e.getCommandName());
            assertEquals(runtimeException.toString(), e.getMessage());
        }
    }


    private class TestCommand extends BasicCommand {
        TestCommand(Runtime runtime) {
            super(runtime);
        }
    }

}