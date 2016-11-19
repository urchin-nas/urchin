package urchin.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import urchin.domain.GroupRepository;
import urchin.domain.cli.GroupCli;
import urchin.domain.model.Group;
import urchin.domain.model.GroupId;
import urchin.domain.model.User;
import urchin.domain.model.UserId;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class GroupServiceTest {

    private static final GroupId GROUP_ID = new GroupId(1);
    private static final UserId USER_ID = new UserId(1);

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private GroupCli groupCli;

    @Mock
    private UserService userService;

    @InjectMocks
    private GroupService groupService;
    private Group group;
    private User user;

    @Before
    public void setup() {
        group = new Group("groupname");
        user = new User(USER_ID, "username", LocalDateTime.now());
    }

    @Test
    public void addGroupIsSavedInGroupRepositoryAndCommandForAddingGroupIsCalled() {
        groupService.addGroup(group);

        verify(groupRepository).saveGroup(group);
        verify(groupCli).addGroup(group);
    }

    @Test
    public void removeGroupRemovesGroupFromGroupRepositoryAndRemoveGroupCommandIsCalled() {
        when(groupRepository.getGroup(GROUP_ID)).thenReturn(Optional.of(group));

        groupService.removeGroup(GROUP_ID);

        verify(groupRepository).removeGroup(GROUP_ID);
        verify(groupCli).removeGroup(group);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeGroupThatDoesNotExistThrowsException() {
        when(groupRepository.getGroup(GROUP_ID)).thenReturn(Optional.empty());

        groupService.removeGroup(GROUP_ID);
    }

    @Test
    public void addUserToGroupCommandIsCalledWhenUserAndGroupExist() {
        when(userService.getUser(USER_ID)).thenReturn(Optional.of(user));
        when(groupRepository.getGroup(GROUP_ID)).thenReturn(Optional.of(group));

        groupService.addUserToGroup(user.getUserId(), GROUP_ID);

        verify(groupCli).addUserToGroup(user, group);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addUserToGroupWhenUserDoesNotExistInRepositoryThrowsException() {
        when(userService.getUser(USER_ID)).thenReturn(Optional.empty());
        when(groupRepository.getGroup(GROUP_ID)).thenReturn(Optional.of(group));

        groupService.addUserToGroup(user.getUserId(), GROUP_ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addUserToGroupWhenGroupDoesNotExistInRepositoryThrowsException() {
        when(userService.getUser(USER_ID)).thenReturn(Optional.of(user));
        when(groupRepository.getGroup(GROUP_ID)).thenReturn(Optional.empty());

        groupService.addUserToGroup(user.getUserId(), GROUP_ID);
    }

}