package urchin.domain.cli;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Optional;

public abstract class BasicCommand {

    protected final Logger LOG = LoggerFactory.getLogger(this.getClass().getName());

    protected final Runtime runtime;

    protected BasicCommand(Runtime runtime) {
        this.runtime = runtime;
    }

    protected Optional<String> executeCommand(String[] command) {
        try {
            Process process = runtime.exec(command);
            process.waitFor();

            if (process.exitValue() != 0) {
                LOG.error("Process returned code: " + process.exitValue());
                throw new CommandException(this.getClass().getName(), process.exitValue());
            }

            String response = IOUtils.toString(process.getInputStream(), Charset.defaultCharset());
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
            throw new CommandException(this.getClass().getName(), e);
        }
    }
}
