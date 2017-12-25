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

import static org.assertj.core.api.Assertions.assertThat;
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
    private User user;

    @Before
    public void setup() {
        groupName = GroupName.of(GROUP_PREFIX + System.currentTimeMillis());
        group = ImmutableGroup.builder()
                .groupId(GroupId.of(1))
                .name(groupName)
                .created(LocalDateTime.now())
                .build();
        user = ImmutableUser.builder()
                .userId(UserId.of(1))
                .username(Username.of(USERNAME_PREFIX + System.currentTimeMillis()))
                .created(LocalDateTime.now())
                .build();
    }

    @Test
    public void addGroupAndCheckIfGroupExistAndRemoveGroupAreExecutedSuccessfully() {
        groupCli.addGroup(groupName);

        assertThat(groupCli.checkIfGroupExist(groupName)).isTrue();

        groupCli.removeGroup(groupName);

        assertThat(groupCli.checkIfGroupExist(groupName)).isFalse();
    }

    @Test
    public void addAndRemoveUserFromGroupAreExecutedSuccessfully() {
        addUserCommand.execute(user.getUsername());
        groupCli.addGroup(groupName);

        groupCli.addUserToGroup(user, group);
        assertThat(groupCli.checkIfUserIsInGroup(user, group)).isTrue();

        groupCli.removeUserFromGroup(user, group);
        assertThat(groupCli.checkIfUserIsInGroup(user, group)).isFalse();

        removeUserCommand.execute(user.getUsername());
    }

    @Test
    public void listGroupsReturnsListOfGroups() {
        List<GroupName> groups = groupCli.listGroups();

        assertThat(groups.isEmpty()).isFalse();
    }

    @Test
    public void listUsersForGroupReturnsListOfUsernames() {
        addUserCommand.execute(user.getUsername());
        groupCli.addGroup(groupName);
        groupCli.addUserToGroup(user, group);

        List<Username> usernames = groupCli.listUsersForGroup(group);

        assertThat(usernames).hasSize(1);
        assertThat(usernames.get(0)).isEqualTo(user.getUsername());
    }

}