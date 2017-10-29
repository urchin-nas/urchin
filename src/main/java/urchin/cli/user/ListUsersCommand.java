package urchin.cli.user;

import org.springframework.stereotype.Component;
import urchin.cli.Command;
import urchin.cli.common.BasicCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ListUsersCommand extends BasicCommand {

    private final Command command;

    public ListUsersCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public List<String> execute() {
        LOG.debug("Listing users");
        Optional<String> response = executeCommand(command.getUserCommand("list-users"));
        String[] users = response.get().split("\n");
        List<String> unixUsers = new ArrayList<>();
        for (String user : users) {
            String[] userValues = user.split(":");
            unixUsers.add(userValues[0]);
        }
        return unixUsers;
    }
}
