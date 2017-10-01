package urchin.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import urchin.domain.cli.GroupCli;
import urchin.domain.model.*;
import urchin.domain.repository.GroupRepository;
import urchin.exception.GroupNotFoundException;
import urchin.exception.UserNotFoundException;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class GroupServiceTest {

    private static final GroupId GROUP_ID = GroupId.of(2);
    private static final UserId USER_ID = UserId.of(1);
    private static final String GROUP_NAME = "groupname";
    private static final Group GROUP = ImmutableGroup.builder()
            .groupId(GROUP_ID)
            .name(GROUP_NAME)
            .created(LocalDateTime.now())
            .build();
    private static final User USER = ImmutableUser.builder()
            .userId(USER_ID)
            .username("username")
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
        when(userService.getUser(USER_ID)).thenThrow(new UserNotFoundException(""));

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
        when(userService.getUser(USER_ID)).thenThrow(new UserNotFoundException(""));
        when(groupRepository.getGroup(GROUP_ID)).thenReturn(GROUP);

        groupService.removeUserFromGroup(USER.getUserId(), GROUP_ID);
    }

    @Test(expected = GroupNotFoundException.class)
    public void removeUserFromGroupWhenGroupDoesNotExistInRepositoryThrowsException() {
        when(userService.getUser(USER_ID)).thenReturn(USER);
        when(groupRepository.getGroup(GROUP_ID)).thenThrow(new GroupNotFoundException(""));

        groupService.removeUserFromGroup(USER.getUserId(), GROUP_ID);
    }

}