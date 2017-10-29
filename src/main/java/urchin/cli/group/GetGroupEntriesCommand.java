package urchin.cli.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.Command;
import urchin.cli.common.BasicCommand;
import urchin.model.group.GroupName;

import java.util.Optional;

@Component
public class GetGroupEntriesCommand extends BasicCommand {

    private static final String GROUP = "%group%";

    private final Command command;

    @Autowired
    public GetGroupEntriesCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public Optional<String> execute(GroupName groupName) {
        LOG.debug("Getting entries for group {}", groupName);
        return executeCommand(command.getGroupCommand("get-group-entries").replace(GROUP, groupName.getValue()));
    }
}