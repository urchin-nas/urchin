package urchin.cli;

import org.springframework.stereotype.Component;

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
