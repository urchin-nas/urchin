package urchin.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import urchin.domain.cli.UserCli;
import urchin.domain.model.Group;
import urchin.domain.model.User;
import urchin.domain.model.UserId;
import urchin.domain.repository.GroupRepository;
import urchin.domain.repository.UserRepository;
import urchin.exception.UserNotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static final String PASSWORD = "password";
    private static final UserId USER_ID = new UserId(1);

    @Mock
    private UserRepository userRepository;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private UserCli userCli;
    @InjectMocks
    private UserService userService;

    private User user;

    @Before
    public void setup() {
        user = new User("username");
    }

    @Test
    public void addUserIsSavedInUserRepositoryAndCommandForAddingUserAndSettingPasswordAreCalled() {
        userService.addUser(user, PASSWORD);

        verify(userRepository).saveUser(user);
        verify(userCli).addUser(user);
        verify(userCli).setSetUserPassword(user, PASSWORD);
    }

    @Test
    public void removeUserRemovesUserFromUserRepositoryAndRemoveUserCommandIsCalled() {
        when(userRepository.getUser(USER_ID)).thenReturn(user);

        userService.removeUser(USER_ID);

        verify(userRepository).removeUser(USER_ID);
        verify(userCli).removeUser(user);
    }

    @Test(expected = UserNotFoundException.class)
    public void removeUserThatDoesNotExistThrowsException() {
        when(userRepository.getUser(USER_ID)).thenThrow(new UserNotFoundException(""));

        userService.removeUser(USER_ID);
    }

    @Test
    public void listGroupsForUserReturnsGroupsExistingInBothOSAndRepository() {
        List<String> groupNames = Arrays.asList("group_1", "group_2");
        when(userRepository.getUser(USER_ID)).thenReturn(user);
        when(userCli.listGroupsForUser(user)).thenReturn(groupNames);
        when(groupRepository.getGroupsByName(groupNames)).thenReturn(Collections.singletonList(new Group("group_1")));

        List<Group> groups = userService.listGroupsForUser(USER_ID);

        assertEquals(1, groups.size());
        assertEquals("group_1", groups.get(0).getName());
    }

    @Test(expected = UserNotFoundException.class)
    public void listGroupsForUserThrowsExceptionWhenUserDoesNotExist() {
        when(userRepository.getUser(USER_ID)).thenThrow(new UserNotFoundException(""));

        userService.listGroupsForUser(USER_ID);
    }

}