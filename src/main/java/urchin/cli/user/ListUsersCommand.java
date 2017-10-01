package urchin.cli.user;

import org.springframework.stereotype.Component;
import urchin.cli.common.BasicCommand;

import java.util.Optional;

@Component
public class ListUsersCommand extends BasicCommand {

    private static final String[] COMMAND = new String[]{
            "cat",
            "/etc/passwd"
    };

    public ListUsersCommand(Runtime runtime) {
        super(runtime);
    }

    public Optional<String> execute() {
        LOG.debug("Listing users");
        return executeCommand(COMMAND);
    }
}
