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
                .clickOnUsers();

        USERS.verifyAtView()
                .clickOnCreateNewUser();

        NEW_USER.verifyAtView()
                .clickOnCancel();

        USERS.verifyAtView()
                .clickOnCreateNewUser();

        NEW_USER.verifyAtView()
                .fillUsername(username)
                .fillPassword(password)
                .clickOnCreateUser();

        USERS.verifyAtView()
                .verifyUsernameListed(username)
                .clickOnUsername(username);

        EDIT_USER.verifyAtView()
                .clickOnBack();

        USERS.verifyAtView()
                .verifyUsernameListed(username)
                .clickOnUsername(username);

        EDIT_USER.verifyAtView()
                .clickOnDeleteUser();

        USERS.verifyAtView()
                .verifyUsernameNotListed(username);


    }

}
