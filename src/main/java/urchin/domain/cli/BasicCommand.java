package urchin.domain.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class BasicCommand {

    protected final Logger LOG = LoggerFactory.getLogger(this.getClass().getName());

    protected final Runtime runtime;

    BasicCommand(Runtime runtime) {
        this.runtime = runtime;
    }

    void executeCommand(String[] command) {
        try {
            Process process = runtime.exec(command);
            process.waitFor();
            if (process.exitValue() != 0) {
                String errorMessage = "Process returned code: " + process.exitValue();
                LOG.error(errorMessage);
                throw new CommandException(this.getClass().getName(), errorMessage);
            }
        } catch (Exception e) {
            LOG.error("Failed to execute command");
            throw new CommandException(this.getClass().getName(), e);
        }
    }
}
