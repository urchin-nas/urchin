package urchin.cli.permission;

import org.springframework.stereotype.Component;
import urchin.cli.common.BasicCommand;

import java.nio.file.Path;
import java.util.Optional;

import static java.util.Arrays.copyOf;

@Component
public class ListFileInformationCommand extends BasicCommand {

    private static final String FILE = "%FILE%";

    private static final String[] COMMAND = new String[]{
            "ls",
            "-ld",
            FILE
    };

    protected ListFileInformationCommand(Runtime runtime) {
        super(runtime);
    }

    public Optional<String> execute(Path file) {
        LOG.debug("Listing file modes for {}", file);
        return executeCommand(setupCommand(file));
    }

    private String[] setupCommand(Path file) {
        String[] command = copyOf(COMMAND, COMMAND.length);
        command[2] = file.toAbsolutePath().toString();
        return command;
    }
}
