package urchin.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import urchin.cli.UserCli;
import urchin.exception.UserNotFoundException;
import urchin.model.group.Group;
import urchin.model.group.GroupId;
import urchin.model.group.GroupName;
import urchin.model.group.ImmutableGroup;
import urchin.model.user.*;
import urchin.repository.GroupRepository;
import urchin.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static final Password PASSWORD = Password.of("password");
    private static final Username USERNAME = Username.of("username");
    private static final UserId USER_ID = UserId.of(1);
    private static final User USER = ImmutableUser.builder()
            .userId(USER_ID)
            .username(USERNAME)
            .created(LocalDateTime.now())
            .build();

    @Mock
    private UserRepository userRepository;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private UserCli userCli;
    @InjectMocks
    private UserService userService;

    @Test
    public void addUserIsSavedInUserRepositoryAndCommandForAddingUserAndSettingPasswordAreCalled() {
        userService.addUser(USERNAME, PASSWORD);

        verify(userRepository).saveUser(USERNAME);
        verify(userCli).addUser(USERNAME);
        verify(userCli).setUserPassword(USERNAME, PASSWORD);
    }

    @Test
    public void removeUserRemovesUserFromUserRepositoryAndRemoveUserCommandIsCalled() {
        when(userRepository.getUser(USER_ID)).thenReturn(USER);

        userService.removeUser(USER_ID);

        verify(userRepository).removeUser(USER_ID);
        verify(userCli).removeUser(USER.getUsername());
    }

    @Test(expected = UserNotFoundException.class)
    public void removeUserThatDoesNotExistThrowsException() {
        when(userRepository.getUser(USER_ID)).thenThrow(new UserNotFoundException(USER_ID));

        userService.removeUser(USER_ID);
    }

    @Test
    public void listGroupsForUserReturnsGroupsExistingInBothOSAndRepository() {
        GroupName group_1 = GroupName.of("group_1");
        List<GroupName> groupNames = Arrays.asList(group_1, GroupName.of("group_2"));
        when(userRepository.getUser(USER_ID)).thenReturn(USER);
        when(userCli.listGroupsForUser(USER)).thenReturn(groupNames);
        when(groupRepository.getGroupsByName(groupNames)).thenReturn(Collections.singletonList(ImmutableGroup.builder()
                .groupId(GroupId.of(1))
                .name(group_1)
                .created(LocalDateTime.now())
                .build())
        );

        List<Group> groups = userService.listGroupsForUser(USER_ID);

        assertThat(groups).hasSize(1);
        assertThat(groups.get(0).getName()).isEqualTo(group_1);
    }

    @Test(expected = UserNotFoundException.class)
    public void listGroupsForUserThrowsExceptionWhenUserDoesNotExist() {
        when(userRepository.getUser(USER_ID)).thenThrow(new UserNotFoundException(USER_ID));

        userService.listGroupsForUser(USER_ID);
    }

}