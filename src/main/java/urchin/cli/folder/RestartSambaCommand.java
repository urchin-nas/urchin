package urchin.cli.folder;

import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;

@Component
public class RestartSambaCommand extends BasicCommand {

    private static final String RESTART_SAMBA = "restart-samba";
    private final Command command;

    public RestartSambaCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute() {
        log.info("Restarting samba service");
        executeCommand(command.getFolderCommand(RESTART_SAMBA));
    }

}
