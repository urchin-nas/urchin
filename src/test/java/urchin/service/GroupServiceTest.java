package urchin.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import urchin.cli.GroupCli;
import urchin.exception.GroupNotFoundException;
import urchin.exception.UserNotFoundException;
import urchin.model.group.Group;
import urchin.model.group.GroupId;
import urchin.model.group.GroupName;
import urchin.model.group.ImmutableGroup;
import urchin.model.user.ImmutableUser;
import urchin.model.user.User;
import urchin.model.user.UserId;
import urchin.model.user.Username;
import urchin.repository.GroupRepository;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class GroupServiceTest {

    private static final GroupId GROUP_ID = GroupId.of(2);
    private static final UserId USER_ID = UserId.of(1);
    private static final GroupName GROUP_NAME = GroupName.of("groupname");
    private static final Group GROUP = ImmutableGroup.builder()
            .groupId(GROUP_ID)
            .name(GROUP_NAME)
            .created(LocalDateTime.now())
            .build();
    private static final User USER = ImmutableUser.builder()
            .userId(USER_ID)
            .username(Username.of("username"))
            .created(LocalDateTime.now())
            .build();

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private GroupCli groupCli;

    @Mock
    private UserService userService;

    @InjectMocks
    private GroupService groupService;


    @Test
    public void addGroupIsSavedInGroupRepositoryAndCommandForAddingGroupIsCalled() {
        groupService.addGroup(GROUP_NAME);

        verify(groupRepository).saveGroup(GROUP_NAME);
        verify(groupCli).addGroup(GROUP_NAME);
    }

    @Test
    public void removeGroupRemovesGroupFromGroupRepositoryAndRemoveGroupCommandIsCalled() {
        when(groupRepository.getGroup(GROUP_ID)).thenReturn(GROUP);

        groupService.removeGroup(GROUP_ID);

        verify(groupRepository).removeGroup(GROUP_ID);
        verify(groupCli).removeGroup(GROUP_NAME);
    }

    @Test(expected = GroupNotFoundException.class)
    public void removeGroupThatDoesNotExistThrowsException() {
        when(groupRepository.getGroup(GROUP_ID)).thenThrow(new GroupNotFoundException(""));

        groupService.removeGroup(GROUP_ID);
    }

    @Test
    public void addUserToGroupCommandIsCalledWhenUserAndGroupExist() {
        when(userService.getUser(USER_ID)).thenReturn(USER);
        when(groupRepository.getGroup(GROUP_ID)).thenReturn(GROUP);

        groupService.addUserToGroup(USER.getUserId(), GROUP_ID);

        verify(groupCli).addUserToGroup(USER, GROUP);
    }

    @Test(expected = UserNotFoundException.class)
    public void addUserToGroupWhenUserDoesNotExistInRepositoryThrowsException() {
        when(userService.getUser(USER_ID)).thenThrow(new UserNotFoundException(USER_ID));

        groupService.addUserToGroup(USER.getUserId(), GROUP_ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addUserToGroupWhenGroupDoesNotExistInRepositoryThrowsException() {
        when(userService.getUser(USER_ID)).thenReturn(USER);
        when(groupRepository.getGroup(GROUP_ID)).thenThrow(new IllegalArgumentException());

        groupService.addUserToGroup(USER.getUserId(), GROUP_ID);
    }

    @Test
    public void removeUserFromGroupCommandIsCalledWhenUserAndGroupExist() {
        when(userService.getUser(USER_ID)).thenReturn(USER);
        when(groupRepository.getGroup(GROUP_ID)).thenReturn(GROUP);

        groupService.removeUserFromGroup(USER.getUserId(), GROUP_ID);

        verify(groupCli).removeUserFromGroup(USER, GROUP);
    }

    @Test(expected = UserNotFoundException.class)
    public void removeUserFromGroupWhenUserDoesNotExistInRepositoryThrowsException() {
        when(userService.getUser(USER_ID)).thenThrow(new UserNotFoundException(USER_ID));

        groupService.removeUserFromGroup(USER.getUserId(), GROUP_ID);
    }

    @Test(expected = GroupNotFoundException.class)
    public void removeUserFromGroupWhenGroupDoesNotExistInRepositoryThrowsException() {
        when(userService.getUser(USER_ID)).thenReturn(USER);
        when(groupRepository.getGroup(GROUP_ID)).thenThrow(new GroupNotFoundException(""));

        groupService.removeUserFromGroup(USER.getUserId(), GROUP_ID);
    }

}