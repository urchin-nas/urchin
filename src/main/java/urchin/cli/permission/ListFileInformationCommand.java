package urchin.cli.permission;

import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;

import java.nio.file.Path;
import java.util.Optional;

@Component
public class ListFileInformationCommand extends BasicCommand {

    private static final String FILE = "%file%";
    private static final String LIST_FILE_INFORMATION = "list-file-information";

    private final Command command;

    protected ListFileInformationCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public Optional<String> execute(Path file) {
        log.debug("Listing file modes for {}", file);
        return executeCommand(command.getPermissionCommand(LIST_FILE_INFORMATION)
                .replace(FILE, file.toAbsolutePath().toString()));
    }
}
