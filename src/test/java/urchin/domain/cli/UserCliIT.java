package urchin.domain.cli;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import urchin.domain.model.User;
import urchin.testutil.CliTestConfiguration;
import urchin.testutil.UnixUserAndGroupCleanup;

import java.util.List;

import static org.junit.Assert.*;
import static urchin.testutil.UnixUserAndGroupCleanup.USERNAME_PREFIX;

@RunWith(SpringRunner.class)
@Import(CliTestConfiguration.class)
public class UserCliIT {

    private static final String PASSWORD = "superSecret";

    @Rule
    @Autowired
    public UnixUserAndGroupCleanup unixUserAndGroupCleanup;

    @Autowired
    private UserCli userCli;

    private User user;

    @Before
    public void setUp() {
        user = new User(USERNAME_PREFIX + System.currentTimeMillis());
    }

    @Test
    public void addUserAndSetUserPasswordAndCheckIfUsernameExistAndRemoveUserAreExecutedSuccessfully() {
        userCli.addUser(user);
        userCli.setSetUserPassword(user, PASSWORD);

        assertTrue(userCli.checkIfUsernameExist(user.getUsername()));

        userCli.removeUser(user);

        assertFalse(userCli.checkIfUsernameExist(user.getUsername()));
    }

    @Test
    public void listUsersReturnsListOfUsers() {
        List<String> strings = userCli.listUsers();

        assertFalse(strings.isEmpty());
    }

    @Test
    public void listGroupsForUserReturnsGroups() {
        userCli.addUser(user);

        List<String> groups = userCli.listGroupsForUser(user);

        assertEquals(1, groups.size());
    }

}