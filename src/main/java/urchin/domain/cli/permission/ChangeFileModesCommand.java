package urchin.domain.cli.permission;

import org.springframework.stereotype.Component;
import urchin.domain.cli.common.BasicCommand;
import urchin.domain.model.FileModes;

import java.nio.file.Path;

import static java.util.Arrays.copyOf;

@Component
public class ChangeFileModesCommand extends BasicCommand {

    private static final String MODE = "%MODE%";
    private static final String FILE = "%FILE%";

    private static final String[] COMMAND = new String[]{
            "chmod",
            "-R",
            MODE,
            FILE
    };

    protected ChangeFileModesCommand(Runtime runtime) {
        super(runtime);
    }

    public void execute(FileModes fileModes, Path file) {
        LOG.debug("Change file mode {}", fileModes);
        executeCommand(setupCommand(fileModes, file));
    }

    private String[] setupCommand(FileModes fileModes, Path file) {
        String[] command = copyOf(COMMAND, COMMAND.length);
        command[2] = fileModes.getModes();
        command[3] = file.toAbsolutePath().toString();
        return command;
    }

}
