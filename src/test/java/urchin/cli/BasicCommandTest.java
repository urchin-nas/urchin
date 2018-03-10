package urchin.cli;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import urchin.exception.CommandException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static java.nio.charset.Charset.defaultCharset;
import static org.apache.commons.io.IOUtils.toInputStream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
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

        assertThat(stringOptional.isPresent()).isTrue();
        assertThat(stringOptional.get()).isEqualTo(SOME_RESPONSE);
    }

    @Test
    public void noResponseIsReturnedOnSuccessfulCommandExecutionIfCommandReturnsNothing() throws IOException {
        InputStream emptyInputStream = toInputStream("", defaultCharset());
        when(process.getInputStream()).thenReturn(emptyInputStream);

        Optional<String> stringOptional = testCommand.executeCommand(COMMAND);

        assertThat(stringOptional.isPresent()).isFalse();
    }

    @Test
    public void exceptionIsThrownWhenCommandDoesNotReturnExitValueZero() {
        int exitValue = -1;
        InputStream errorStream = toInputStream("some error occured", defaultCharset());
        when(process.exitValue()).thenReturn(exitValue);
        when(process.getErrorStream()).thenReturn(errorStream);

        try {
            testCommand.executeCommand(COMMAND);
            fail("Expected CommandException");
        } catch (CommandException e) {
            assertThat(e.getExitValue().intValue()).isEqualTo(exitValue);
            assertThat(e.getCommand()).isEqualTo(TestCommand.class);
            assertThat(e.getMessage()).isEqualTo(CommandException.PROCESS_RETURNED_EXIT_VALUE + exitValue);
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
            assertThat(e.getExitValue()).isNull();
            assertThat(e.getCommand()).isEqualTo(TestCommand.class);
            assertThat(e.getMessage()).isEqualTo(runtimeException.toString());
        }
    }


    private class TestCommand extends BasicCommand {
        TestCommand(Runtime runtime) {
            super(runtime);
        }
    }

}