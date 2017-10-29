package urchin.cli;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import urchin.cli.user.AddUserCommand;
import urchin.cli.user.RemoveUserCommand;
import urchin.model.group.Group;
import urchin.model.group.GroupId;
import urchin.model.group.GroupName;
import urchin.model.group.ImmutableGroup;
import urchin.model.user.ImmutableUser;
import urchin.model.user.User;
import urchin.model.user.UserId;
import urchin.model.user.Username;
import urchin.testutil.CliTestConfiguration;
import urchin.testutil.UnixUserAndGroupCleanup;

import java.time.LocalDateTime;
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
    private GroupName groupName;

    @Before
    public void setup() {
        groupName = GroupName.of(GROUP_PREFIX + System.currentTimeMillis());
        group = ImmutableGroup.builder()
                .groupId(GroupId.of(1))
                .name(groupName)
                .created(LocalDateTime.now())
                .build();
    }

    @Test
    public void addGroupAndCheckIfGroupExistAndRemoveGroupAreExecutedSuccessfully() {
        groupCli.addGroup(groupName);

        assertTrue(groupCli.checkIfGroupExist(groupName));

        groupCli.removeGroup(groupName);

        assertFalse(groupCli.checkIfGroupExist(groupName));
    }

    @Test
    public void addAndRemoveUserFromGroupAreExecutedSuccessfully() {
        User user = ImmutableUser.builder()
                .userId(UserId.of(1))
                .username(Username.of(USERNAME_PREFIX + System.currentTimeMillis()))
                .created(LocalDateTime.now())
                .build();
        addUserCommand.execute(user.getUsername());
        groupCli.addGroup(groupName);

        groupCli.addUserToGroup(user, group);
        assertTrue(groupCli.checkIfUserIsInGroup(user, group));

        groupCli.removeUserFromGroup(user, group);
        assertFalse(groupCli.checkIfUserIsInGroup(user, group));

        removeUserCommand.execute(user.getUsername());
    }

    @Test
    public void listGroupsReturnsListOfGroups() {
        List<GroupName> groups = groupCli.listGroups();

        assertFalse(groups.isEmpty());
    }

}