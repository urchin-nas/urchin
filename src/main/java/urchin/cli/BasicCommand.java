package urchin.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import urchin.exception.CommandException;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static urchin.cli.CommandUtil.readResponse;

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
            process.waitFor(10, TimeUnit.SECONDS);

            if (process.exitValue() != 0) {
                String error = readResponse(process.getErrorStream());
                log.error("Process returned error: {}", error);
                throw new CommandException(this.getClass(), process.exitValue());
            }

            String response = readResponse(process.getInputStream());

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
