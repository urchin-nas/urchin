package urchin.cli.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;
import urchin.model.user.Password;
import urchin.model.user.Shadow;

@Component
public class VerifyShadowPasswordCommand extends BasicCommand {

    private static final String VERIFY_SHADOW_PASSWORD = "verify-shadow-password";
    private static final String ENCRYPTION_METHOD = "%method%";
    private static final String PASSWORD = "%password%";
    private static final String SALT = "%salt%";

    private final Command command;

    @Autowired
    public VerifyShadowPasswordCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public String execute(Password password, Shadow shadow) {
        return executeCommand(command.getUserCommand(VERIFY_SHADOW_PASSWORD)
                .replace(ENCRYPTION_METHOD, shadow.getEncryptionMethod())
                .replace(PASSWORD, password.getValue())
                .replace(SALT, shadow.getSalt())
        ).orElseThrow(RuntimeException::new);
    }

}
