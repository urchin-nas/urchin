package urchin.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import urchin.domain.cli.UserCli;
import urchin.domain.model.User;
import urchin.domain.model.UserId;
import urchin.domain.repository.UserRepository;

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
        when(userRepository.getUser(USER_ID)).thenReturn(Optional.of(user));

        userService.removeUser(USER_ID);

        verify(userRepository).removeUser(USER_ID);
        verify(userCli).removeUser(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeUserThatDoesNotExistThrowsException() {
        when(userRepository.getUser(USER_ID)).thenReturn(Optional.empty());

        userService.removeUser(USER_ID);
    }

}