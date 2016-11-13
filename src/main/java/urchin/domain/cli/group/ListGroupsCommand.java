package urchin.domain.cli.group;

import org.springframework.stereotype.Component;
import urchin.domain.cli.common.BasicCommand;

import java.util.Optional;

@Component
public class ListGroupsCommand extends BasicCommand {

    private static final String[] COMMAND = new String[]{
            "cat",
            "/etc/group"
    };

    public ListGroupsCommand(Runtime runtime) {
        super(runtime);
    }

    public Optional<String> execute() {
        LOG.debug("Listing groups");
        return executeCommand(COMMAND);
    }
}
