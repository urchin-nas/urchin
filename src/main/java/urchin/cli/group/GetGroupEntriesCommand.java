package urchin.cli.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;
import urchin.model.group.GroupName;

import java.util.Optional;

@Component
public class GetGroupEntriesCommand extends BasicCommand {

    private static final String GROUP = "%group%";
    private static final String GET_GROUP_ENTRIES = "get-group-entries";

    private final Command command;

    @Autowired
    public GetGroupEntriesCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public Optional<String> execute(GroupName groupName) {
        log.debug("Getting entries for group {}", groupName);
        return executeCommand(command.getGroupCommand(GET_GROUP_ENTRIES)
                .replace(GROUP, groupName.getValue()));
    }
}
