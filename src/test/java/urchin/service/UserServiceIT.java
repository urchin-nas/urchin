package urchin.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import urchin.domain.UserRepository;
import urchin.domain.cli.user.CheckIfUsernameExistCommand;
import urchin.domain.model.User;
import urchin.domain.model.UserId;
import urchin.testutil.TestApplication;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static urchin.testutil.OsAssumption.ignoreWhenWindowsOrMac;

public class UserServiceIT extends TestApplication {

    private static final String USERNAME_PREFIX = "urchin_";
    private static final String PASSWORD = "superSecret";

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CheckIfUsernameExistCommand checkIfUsernameExistCommand;

    private User user;

    @Before
    public void setup() {
        ignoreWhenWindowsOrMac();
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

    @Test
    public void addingUserWithoutUsernameShouldFail() {
        user = new User("");

        try {
            userService.addUser(user, PASSWORD);
            fail("expected addUser to throw exception");
        } catch (Exception e) {
            List<User> matchingUsers = userService.getUsers().stream()
                    .filter(foundUser -> foundUser.getUsername().equals(user.getUsername()))
                    .collect(Collectors.toList());

            assertEquals(0, matchingUsers.size());
            assertFalse(checkIfUsernameExistCommand.execute(user.getUsername()));
        }

    }

}