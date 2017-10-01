package urchin.cli.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.common.BasicCommand;
import urchin.model.GroupName;

import java.util.Optional;

import static java.util.Arrays.copyOf;

@Component
public class GetGroupEntriesCommand extends BasicCommand {

    private static final String GROUP_NAME = "%groupname%";

    private static final String[] COMMAND = new String[]{
            "getent",
            "group",
            GROUP_NAME,
    };

    @Autowired
    public GetGroupEntriesCommand(Runtime runtime) {
        super(runtime);
    }

    public Optional<String> execute(GroupName groupName) {
        LOG.debug("Getting entries for group {}", groupName);
        return executeCommand(setupCommand(groupName));
    }

    private String[] setupCommand(GroupName groupName) {
        String[] command = copyOf(COMMAND, COMMAND.length);
        command[2] = groupName.getValue();
        return command;
    }
}
