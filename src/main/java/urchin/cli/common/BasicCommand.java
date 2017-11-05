package urchin.cli.common;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static java.nio.charset.Charset.defaultCharset;

public abstract class BasicCommand {

    protected final Logger LOG = LoggerFactory.getLogger(this.getClass().getName());

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
                LOG.debug("Process failed with error: " + IOUtils.toString(process.getErrorStream(), defaultCharset()));
                LOG.error("Process returned code: " + process.exitValue());
                throw new CommandException(this.getClass(), process.exitValue());
            }

            String response = IOUtils.toString(process.getInputStream(), defaultCharset());
            if (response.length() > 0) {
                LOG.debug("Response: " + response);
                return Optional.of(response);
            } else {
                return Optional.empty();
            }

        } catch (CommandException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Failed to execute command");
            throw new CommandException(this.getClass(), e);
        }
    }
}
