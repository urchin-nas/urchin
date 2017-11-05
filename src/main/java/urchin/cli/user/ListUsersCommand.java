package urchin.cli.user;

import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ListUsersCommand extends BasicCommand {

    private static final String LIST_USERS = "list-users";
    private final Command command;

    public ListUsersCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public List<String> execute() {
        LOG.debug("Listing users");

        Optional<String> response = executeCommand(command.getUserCommand(LIST_USERS));

        String[] users = response.get().split("\n");
        List<String> unixUsers = new ArrayList<>();
        for (String user : users) {
            String[] userValues = user.split(":");
            unixUsers.add(userValues[0]);
        }
        return unixUsers;
    }
}
