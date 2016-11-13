package urchin.domain.cli;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import urchin.domain.cli.user.AddUserCommand;
import urchin.domain.cli.user.RemoveUserCommand;
import urchin.domain.model.Group;
import urchin.domain.model.User;
import urchin.testutil.CliTestConfiguration;
import urchin.testutil.UnixUserAndGroupCleanup;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static urchin.testutil.UnixUserAndGroupCleanup.GROUP_PREFIX;
import static urchin.testutil.UnixUserAndGroupCleanup.USERNAME_PREFIX;

@RunWith(SpringRunner.class)
@Import(CliTestConfiguration.class)
public class GroupCliIT {

    @Rule
    @Autowired
    public UnixUserAndGroupCleanup unixUserAndGroupCleanup;

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

    @Test
    public void listGroupsReturnsListOfGroups() {
        List<String> groups = groupCli.listGroups();

        assertFalse(groups.isEmpty());
    }

}