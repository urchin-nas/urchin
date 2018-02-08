package urchin.cli.user;

import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ListUsersCommand extends BasicCommand {

    private static final String LIST_USERS = "list-users";
    private final Command command;

    public ListUsersCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public List<String> execute() {
        log.debug("Listing users");

        Optional<String> response = executeCommand(command.getUserCommand(LIST_USERS));

        return Arrays.stream(response.map(s -> s.split("\n")).orElse(new String[0]))
                .map(user -> user.split(":")[0])
                .collect(Collectors.toList());
    }
}
