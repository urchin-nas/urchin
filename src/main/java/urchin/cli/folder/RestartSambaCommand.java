package urchin.cli.folder;

import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;

@Component
public class RestartSambaCommand extends BasicCommand {

    private static final String[] COMMAND = {
            "sudo",
            "/etc/init.d/samba",
            "restart"
    };

    public RestartSambaCommand(Runtime runtime) {
        super(runtime);
    }

    public void execute() {
        LOG.info("Restarting samba service");
        executeCommand(COMMAND);
    }

}
