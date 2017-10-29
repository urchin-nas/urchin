package urchin.cli.permission;

import org.springframework.stereotype.Component;
import urchin.cli.Command;
import urchin.cli.common.BasicCommand;

import java.nio.file.Path;
import java.util.Optional;

@Component
public class ListFileInformationCommand extends BasicCommand {

    private static final String FILE = "%file%";

    private final Command command;

    protected ListFileInformationCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public Optional<String> execute(Path file) {
        LOG.debug("Listing file modes for {}", file);
        return executeCommand(command.getPermissionCommand("list-file-information").replace(FILE, file.toAbsolutePath().toString()));
    }
}
