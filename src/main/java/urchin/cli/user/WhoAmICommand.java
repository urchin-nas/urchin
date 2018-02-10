package urchin.cli.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;
import urchin.model.user.ImmutableLinuxUser;
import urchin.model.user.LinuxUser;
import urchin.model.user.Username;

@Component
public class WhoAmICommand extends BasicCommand {

    private static final String WHO_AM_I = "who-am-i";

    private final Command command;

    @Autowired
    public WhoAmICommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public LinuxUser execute() {
        return executeCommand(command.getUserCommand(WHO_AM_I)).map(username -> ImmutableLinuxUser.of(Username.of(username.trim()))
        ).orElseThrow(RuntimeException::new);
    }
}
