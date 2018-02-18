package urchin.cli;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import urchin.exception.CommandException;

import java.util.Optional;

import static java.nio.charset.Charset.defaultCharset;

public abstract class BasicCommand {

    protected final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private final Runtime runtime;

    protected BasicCommand(Runtime runtime) {
        this.runtime = runtime;
    }

    protected Optional<String> executeCommand(String command) {
        return executeCommand(command.split(" "));
    }

    protected Optional<String> executeCommand(String[] command) {
        try {
            Process process = runtime.exec(command);
            process.waitFor();

            if (process.exitValue() != 0) {
                String error = IOUtils.toString(process.getErrorStream(), defaultCharset());
                log.error("Process returned error: {}", error);
                throw new CommandException(this.getClass(), process.exitValue());
            }

            String response = IOUtils.toString(process.getInputStream(), defaultCharset());
            if (response.length() > 0) {
                log.debug("Response: {}", response);
                return Optional.of(response);
            } else {
                return Optional.empty();
            }

        } catch (CommandException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to execute command");
            throw new CommandException(this.getClass(), e);
        }
    }
}
