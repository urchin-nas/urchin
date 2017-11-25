package urchin.selenium;

import org.junit.Test;
import urchin.selenium.testutil.SeleniumTestApplication;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static urchin.testutil.UnixUserAndGroupCleanup.USERNAME_PREFIX;

public class UserITCase extends SeleniumTestApplication {

    @Test
    public void createAndDeleteUser() {
        String username = USERNAME_PREFIX + System.currentTimeMillis();

        homeView.goTo();

        menuView.verifyAtView()
                .clickUsersLink();

        usersView.verifyAtView()
                .clickCreateNewUserLink();

        newUserView.verifyAtView()
                .fillUsername(username)
                .fillPassword(randomAlphanumeric(10))
                .clickCreateUserButton();

        usersView.verifyAtView()
                .verifyUsernameListed(username)
                .clickUsernameLink(username);

        editUserView.verifyAtView()
                .clickDeleteUserButton();

        usersView.verifyAtView()
                .verifyUsernameNotListed(username);


    }

}
