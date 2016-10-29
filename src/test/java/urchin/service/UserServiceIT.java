package urchin.service;

import org.junit.Before;
import org.junit.Test;
import urchin.domain.UserRepository;
import urchin.domain.cli.user.AddUserCommand;
import urchin.domain.cli.user.CheckIfUsernameExistCommand;
import urchin.domain.cli.user.RemoveUserCommand;
import urchin.domain.cli.user.SetUserPasswordCommand;
import urchin.domain.model.User;
import urchin.domain.model.UserId;
import urchin.testutil.H2Application;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static urchin.testutil.OsAssumption.ignoreWhenWindowsOrMac;

public class UserServiceIT extends H2Application {

    private static final Runtime runtime = Runtime.getRuntime();
    private static final String USERNAME_PREFIX = "urchin_";
    private static final String PASSWORD = "superSecret";

    private UserService userService;
    private User user;
    private UserRepository userRepository;
    private CheckIfUsernameExistCommand checkIfUsernameExistCommand;

    @Before
    public void setup() {
        ignoreWhenWindowsOrMac();

        userRepository = new UserRepository(jdbcTemplate);
        AddUserCommand addUserCommand = new AddUserCommand(runtime);
        SetUserPasswordCommand setUserPasswordCommand = new SetUserPasswordCommand(runtime);
        RemoveUserCommand removeUserCommand = new RemoveUserCommand(runtime);
        checkIfUsernameExistCommand = new CheckIfUsernameExistCommand(runtime);

        userService = new UserService(
                userRepository,
                addUserCommand,
                setUserPasswordCommand,
                removeUserCommand
        );
    }

    @Test
    public void userIsCreatedAndRemoved() {
        user = new User(USERNAME_PREFIX + System.currentTimeMillis());

        UserId userId = userService.addUser(user, PASSWORD);

        assertTrue(userRepository.getUser(userId).isPresent());
        assertTrue(checkIfUsernameExistCommand.execute(user.getUsername()));

        userService.removeUser(userId);

        assertFalse(userRepository.getUser(userId).isPresent());
        assertFalse(checkIfUsernameExistCommand.execute(user.getUsername()));
    }

}