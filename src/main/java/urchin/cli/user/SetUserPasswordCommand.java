package urchin.cli.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.common.CommandException;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

import static java.util.Arrays.copyOf;

@Component
public class SetUserPasswordCommand {

    private static final Logger LOG = LoggerFactory.getLogger(SetUserPasswordCommand.class);
    private static final String USERNAME = "%username%";

    private static final String[] COMMAND = new String[]{
            "sudo",
            "passwd",
            USERNAME,
    };

    private final Runtime runtime;

    @Autowired
    public SetUserPasswordCommand(Runtime runtime) {
        this.runtime = runtime;
    }

    public void execute(String username, String password) {
        LOG.info("Setting password for user {}", username);
        String[] command = setupCommand(username);

        try {
            Process process = runtime.exec(command);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            bufferedWriter.write(password);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.write(password);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            process.waitFor();
            if (process.exitValue() != 0) {
                throw new CommandException(this.getClass().getName(), process.exitValue());
            }
        } catch (Exception e) {
            LOG.error("Failed to execute command");
            throw new CommandException(this.getClass().getName(), e);
        }
    }

    private String[] setupCommand(String username) {
        String[] command = copyOf(COMMAND, COMMAND.length);
        command[2] = username;
        return command;
    }
}
