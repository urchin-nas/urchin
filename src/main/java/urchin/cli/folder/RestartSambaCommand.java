package urchin.cli.folder;

import org.springframework.stereotype.Component;
import urchin.cli.Command;
import urchin.cli.common.BasicCommand;

@Component
public class RestartSambaCommand extends BasicCommand {

    private final Command command;

    public RestartSambaCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute() {
        LOG.info("Restarting samba service");
        executeCommand(command.getFolderCommand("restart-samba"));
    }

}
