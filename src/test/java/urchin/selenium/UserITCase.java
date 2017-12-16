package urchin.selenium;

import org.junit.Test;
import urchin.selenium.testutil.SeleniumTest;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static urchin.testutil.UnixUserAndGroupCleanup.USERNAME_PREFIX;

public class UserITCase extends SeleniumTest {

    @Test
    public void createAndDeleteUser() {
        String username = USERNAME_PREFIX + System.currentTimeMillis();
        String password = randomAlphanumeric(10);

        HOME.goTo();

        NAVIGATION.verifyAtView()
                .clickUsersLink();

        USERS.verifyAtView()
                .clickCreateNewUserLink();

        NEW_USER.verifyAtView()
                .clickCancelButton();

        USERS.verifyAtView()
                .clickCreateNewUserLink();

        NEW_USER.verifyAtView()
                .fillUsername(username)
                .fillPassword(password)
                .clickCreateUserButton();

        USERS.verifyAtView()
                .verifyUsernameListed(username)
                .clickUsernameLink(username);

        EDIT_USER.verifyAtView()
                .clickBackButton();

        USERS.verifyAtView()
                .verifyUsernameListed(username)
                .clickUsernameLink(username);

        EDIT_USER.verifyAtView()
                .clickDeleteUserButton();

        USERS.verifyAtView()
                .verifyUsernameNotListed(username);


    }

}
