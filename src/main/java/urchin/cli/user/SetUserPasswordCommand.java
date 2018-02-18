package urchin.cli.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.Command;
import urchin.exception.CommandException;
import urchin.model.user.Password;
import urchin.model.user.Username;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

@Component
public class SetUserPasswordCommand {

    private static final String USERNAME = "%username%";
    private static final String SET_USER_PASSWORD = "set-user-password";

    private final Logger log = LoggerFactory.getLogger(SetUserPasswordCommand.class);
    private final Runtime runtime;
    private final Command command;

    @Autowired
    public SetUserPasswordCommand(Runtime runtime, Command command) {
        this.runtime = runtime;
        this.command = command;
    }

    public void execute(Username username, Password password) {
        log.info("Setting password for user {}", username);
        String[] cmd = command.getUserCommand(SET_USER_PASSWORD)
                .replace(USERNAME, username.getValue())
                .split(" ");

        try {
            Process process = runtime.exec(cmd);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            bufferedWriter.write(password.getValue());
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.write(password.getValue());
            bufferedWriter.newLine();
            bufferedWriter.flush();
            process.waitFor();
            if (process.exitValue() != 0) {
                throw new CommandException(this.getClass(), process.exitValue());
            }
        } catch (Exception e) {
            log.error("Failed to execute command");
            throw new CommandException(this.getClass(), e);
        }
    }
}
