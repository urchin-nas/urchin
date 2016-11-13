package urchin.domain.cli;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import urchin.domain.model.User;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@Import(CliTestConfiguration.class)
public class UserCliIT {

    private static final String USERNAME_PREFIX = "urchin_u_";
    private static final String PASSWORD = "superSecret";

    @Autowired
    private UserCli userCli;

    @Test
    public void addUserAndSetUserPasswordAndCheckIfUsernameExistAndRemoveUserAreExecutedSuccessfully() {
        User user = new User(USERNAME_PREFIX + System.currentTimeMillis());

        userCli.addUser(user);
        userCli.setSetUserPassword(user, PASSWORD);

        assertTrue(userCli.checkIfUsernameExist(user.getUsername()));

        userCli.removeUser(user);

        assertFalse(userCli.checkIfUsernameExist(user.getUsername()));
    }

}