package urchin.domain.cli.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.domain.cli.BasicCommand;
import urchin.domain.model.Group;

import static java.util.Arrays.copyOf;

@Component
public class AddGroupCommand extends BasicCommand {

    private static final String GROUP = "%group%";

    private static final String[] COMMAND = new String[]{
            "sudo",
            "addgroup",
            GROUP,
    };

    @Autowired
    public AddGroupCommand(Runtime runtime) {
        super(runtime);
    }

    public void execute(Group group) {
        LOG.debug("Creating group {}", group.getName());
        executeCommand(setupCommand(group));
    }

    private String[] setupCommand(Group group) {
        String[] command = copyOf(COMMAND, COMMAND.length);
        command[2] = group.getName();
        return command;
    }
}
