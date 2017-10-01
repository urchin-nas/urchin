package urchin.testutil;

import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import urchin.cli.GroupCli;
import urchin.cli.UserCli;
import urchin.model.group.GroupName;
import urchin.model.user.Username;

import java.util.List;
import java.util.stream.Collectors;

@TestComponent
public class UnixUserAndGroupCleanup extends ExternalResource {

    private static final Logger LOG = LoggerFactory.getLogger(UnixUserAndGroupCleanup.class);

    public static final String GROUP_PREFIX = "urchin_g_";
    public static final String USERNAME_PREFIX = "urchin_u_";

    private final UserCli userCli;
    private final GroupCli groupCli;

    @Autowired
    public UnixUserAndGroupCleanup(UserCli userCli, GroupCli groupCli) {
        this.userCli = userCli;
        this.groupCli = groupCli;
    }

    @Override
    protected void after() {
        LOG.info("Cleaning up any users and groups remaining after test(s)");
        removeTestUsers();
        removeTestGroups();
        super.after();
    }

    private void removeTestUsers() {
        List<Username> usersToRemove = userCli.listUsers().stream()
                .filter(user -> user.startsWith(USERNAME_PREFIX))
                .map(Username::of)
                .collect(Collectors.toList());

        usersToRemove.forEach(userCli::removeUser);
    }

    private void removeTestGroups() {
        List<GroupName> groupsToRemove = groupCli.listGroups().stream()
                .filter(group -> group.getValue().startsWith(GROUP_PREFIX))
                .collect(Collectors.toList());

        groupsToRemove.forEach(groupCli::removeGroup);
    }
}
