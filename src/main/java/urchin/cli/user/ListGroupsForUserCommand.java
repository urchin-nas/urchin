package urchin.cli.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.Command;
import urchin.cli.common.BasicCommand;
import urchin.model.group.GroupName;
import urchin.model.user.User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ListGroupsForUserCommand extends BasicCommand {

    private static final String USER = "%user%";
    private static final String LIST_GROUPS = "list-groups";

    private final Command command;

    @Autowired
    public ListGroupsForUserCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public List<GroupName> execute(User user) {
        Optional<String> response = executeCommand(command.getUserCommand(LIST_GROUPS)
                .replace(USER, user.getUsername().getValue()));

        return Arrays.stream(response.get().split(":")[1].trim().split(" "))
                .map(GroupName::of)
                .collect(Collectors.toList());
    }
}
