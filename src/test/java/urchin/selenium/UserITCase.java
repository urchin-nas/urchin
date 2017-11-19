package urchin.selenium;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import urchin.selenium.view.*;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static urchin.testutil.UnixUserAndGroupCleanup.USERNAME_PREFIX;

public class UserITCase extends SeleniumTestApplication {

    @Autowired
    private HomeView homeView;
    @Autowired
    private MenuView menuView;
    @Autowired
    private UsersView usersView;
    @Autowired
    private NewUserView newUserView;
    @Autowired
    private EditUserView editUserView;

    @Test
    public void createAndDeleteUser() {
        String username = USERNAME_PREFIX + System.currentTimeMillis();

        homeView.goToView();

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
