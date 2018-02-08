package urchin.cli.group;

import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;
import urchin.model.group.GroupName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class ListGroupsCommand extends BasicCommand {

    private static final String LIST_GROUPS = "list-groups";
    private final Command command;

    public ListGroupsCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public List<GroupName> execute() {
        log.debug("Listing groups");
        Optional<String> response = executeCommand(command.getGroupCommand(LIST_GROUPS));

        if (response.isPresent()) {
            String[] groups = response.get().split(":\n");
            List<GroupName> unixGroups = new ArrayList<>();
            for (String group : groups) {
                String[] userValues = group.split(":");
                unixGroups.add(GroupName.of(userValues[0]));
            }

            return unixGroups;
        }
        return Collections.emptyList();
    }
}
