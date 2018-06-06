package urchin.cli.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;
import urchin.model.user.LinuxUser;

import java.util.Arrays;

@Component
public class GetShadowCommand extends BasicCommand {

    private static final String GET_SHADOW = "get-shadow";
    private static final String USERNAME = "%username%";

    private final Command command;

    @Autowired
    public GetShadowCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public String execute(LinuxUser linuxUser) {
        return executeCommand(command.getUserCommand(GET_SHADOW).replace(USERNAME, linuxUser.getUsername().getValue()))
                .map(shadow -> parseResponse(linuxUser, shadow))
                .orElseThrow(RuntimeException::new);
    }

    private String parseResponse(LinuxUser linuxUser, String response) {
        return Arrays.stream(response.split(System.lineSeparator()))
                .filter(line -> line.startsWith(linuxUser.getUsername().getValue()))
                .findFirst()
                .map(s -> s.replaceFirst(String.format("%s:", linuxUser.getUsername().getValue()), ""))
                .orElseThrow(RuntimeException::new);
    }
}
