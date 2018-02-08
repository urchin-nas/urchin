package urchin.cli.permission;

import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;
import urchin.model.group.GroupName;
import urchin.model.permission.Acl;
import urchin.model.permission.ImmutableAcl;
import urchin.model.permission.ImmutableAclPermission;
import urchin.model.user.Username;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetAclCommand extends BasicCommand {

    private final Command command;

    protected GetAclCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public Acl execute(Path path) {
        log.debug("fetching ACL for {}", path);

        return executeCommand(command.getPermissionCommand("get-acl")
                .replace("%path%", path.toAbsolutePath().toString()))
                .map(this::parseResponse)
                .orElse(ImmutableAcl.builder().build());
    }

    private Acl parseResponse(String response) {
        ImmutableAcl.Builder builder = ImmutableAcl.builder();

        List<String[]> acl = Arrays.stream(response.split("\n"))
                .filter(s -> !s.startsWith("#"))
                .map(s -> s.split(":"))
                .collect(Collectors.toList());

        acl.stream()
                .filter(a -> a.length == 3)
                .forEach(a -> {
                    if (!a[1].isEmpty()) {
                        if (a[0].equals("group")) {
                            builder.putGroups(GroupName.of(a[1]), ImmutableAclPermission.of(a[2]));
                        } else {
                            builder.putUsers(Username.of(a[1]), ImmutableAclPermission.of(a[2]));
                        }
                    }
                });

        acl.stream()
                .filter(a -> a.length == 4)
                .forEach(a -> {
                    if (!a[2].isEmpty()) {
                        if (a[1].equals("group")) {
                            builder.putGroups(GroupName.of(a[2]), ImmutableAclPermission.of(a[3]));
                        } else {
                            builder.putUsers(Username.of(a[2]), ImmutableAclPermission.of(a[3]));
                        }
                    }
                });

        return builder.build();
    }
}
