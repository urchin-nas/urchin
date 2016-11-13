package urchin.domain.cli;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import urchin.configuration.RuntimeConfiguration;
import urchin.domain.cli.group.*;
import urchin.domain.cli.user.AddUserCommand;
import urchin.domain.cli.user.RemoveUserCommand;
import urchin.domain.model.Group;
import urchin.domain.model.User;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        RuntimeConfiguration.class,
        GroupCli.class,
        AddGroupCommand.class,
        GetGroupEntriesCommand.class,
        RemoveGroupCommand.class,
        AddUserToGroupCommand.class,
        RemoveUserFromGroupCommand.class,
        AddUserCommand.class,
        RemoveUserCommand.class
})
public class GroupCliIT {

    private static final String GROUP_PREFIX = "urchin_g_";
    private static final String USERNAME_PREFIX = "urchin_u_";

    @Autowired
    private GroupCli groupCli;

    @Autowired
    private AddUserCommand addUserCommand;

    @Autowired
    private RemoveUserCommand removeUserCommand;

    private Group group;

    @Before
    public void setup() {
        group = new Group(GROUP_PREFIX + System.currentTimeMillis());
    }

    @Test
    public void addGroupAndCheckIfGroupExistAndRemoveGroupAreExecutedSuccessfully() {
        groupCli.addGroup(group);

        assertTrue(groupCli.checkIfGroupExist(group.getName()));

        groupCli.removeGroup(group);

        assertFalse(groupCli.checkIfGroupExist(group.getName()));
    }

    @Test
    public void addAndRemoveUserFromGroupAreExecutedSuccessfully() {
        User user = new User(USERNAME_PREFIX + System.currentTimeMillis());
        addUserCommand.execute(user);
        groupCli.addGroup(group);

        groupCli.addUserToGroup(user, group);
        assertTrue(groupCli.checkIfUserIsInGroup(user, group));

        groupCli.removeUserFromGroup(user, group);
        assertFalse(groupCli.checkIfUserIsInGroup(user, group));

        removeUserCommand.execute(user);
    }

}