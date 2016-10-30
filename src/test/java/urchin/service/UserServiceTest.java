package urchin.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import urchin.domain.UserRepository;
import urchin.domain.cli.user.AddUserCommand;
import urchin.domain.cli.user.RemoveUserCommand;
import urchin.domain.cli.user.SetUserPasswordCommand;
import urchin.domain.model.User;
import urchin.domain.model.UserId;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static final String PASSWORD = "password";
    private static final UserId USER_ID = new UserId(1);

    @Mock
    private UserRepository userRepository;
    @Mock
    private AddUserCommand addUserCommand;
    @Mock
    private SetUserPasswordCommand setUserPasswordCommand;
    @Mock
    private RemoveUserCommand removeUserCommand;

    private UserService userService;
    private User user;

    @Before
    public void setup() {
        userService = new UserService(
                userRepository,
                addUserCommand,
                setUserPasswordCommand,
                removeUserCommand
        );
        user = new User("username");
    }

    @Test
    public void addUserIsSavedInUserRepositoryAndCommandForAddingUserAndSettingPasswordAreCalled() {
        userService.addUser(user, PASSWORD);

        verify(userRepository).saveUser(user);
        verify(addUserCommand).execute(user);
        verify(setUserPasswordCommand).execute(user, PASSWORD);
    }

    @Test
    public void removeUserRemovesUserFromUserRepositoryAndRemoveUserCommandIsCalled() {
        when(userRepository.getUser(USER_ID)).thenReturn(Optional.of(user));

        userService.removeUser(USER_ID);

        verify(userRepository).removeUser(USER_ID);
        verify(removeUserCommand).execute(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeUserThatDoesNotExistThrowsException() {
        when(userRepository.getUser(USER_ID)).thenReturn(Optional.empty());

        userService.removeUser(USER_ID);
    }

}