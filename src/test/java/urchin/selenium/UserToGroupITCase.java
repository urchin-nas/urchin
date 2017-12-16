package urchin.selenium;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import urchin.selenium.testutil.SeleniumTestApplication;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static urchin.testutil.UnixUserAndGroupCleanup.GROUP_PREFIX;
import static urchin.testutil.UnixUserAndGroupCleanup.USERNAME_PREFIX;

public class UserToGroupITCase extends SeleniumTestApplication {

    private static String groupName;
    private static String username;

    @BeforeClass
    public static void setUp() {
        groupName = GROUP_PREFIX + System.currentTimeMillis();
        username = USERNAME_PREFIX + System.currentTimeMillis();

        NEW_GROUP.goTo()
                .fillGroupName(groupName)
                .clickCreateGroupButton();

        GROUPS.verifyGroupNameListed(groupName);

        NEW_USER.goTo()
                .fillUsername(username)
                .fillPassword(randomAlphanumeric(10))
                .clickCreateUserButton();

        USERS.verifyUsernameListed(username);
    }

    @AfterClass
    public static void tearDown() {
        USERS.goTo()
                .clickUsernameLink(username);

        EDIT_USER.verifyAtView()
                .clickDeleteUserButton();

        GROUPS.goTo()
                .clickGroupNameLink(groupName);

        EDIT_GROUP.verifyAtView()
                .clickDeleteGroupButton();
    }

    @Test
    public void userCanBeAddedAndRemovedFromUserView() {
        USERS.goTo()
                .verifyUsernameListed(username)
                .clickUsernameLink(username);

        EDIT_USER.verifyAtView()
                .selectGroup(groupName)
                .clickAddGroupButton()
                .verifyGroupListed(groupName)
                .clickGroupLink(groupName);

        EDIT_GROUP.verifyAtView()
                .verifyUserListed(username)
                .clickUserLink(username);

        EDIT_USER.verifyAtView()
                .clickRemoveGroupButton(groupName)
                .verifyGroupNotListed(groupName);
    }

    @Test
    public void userCanBeAddedAndRemovedFromGroupView() {
        GROUPS.goTo()
                .verifyGroupNameListed(groupName)
                .clickGroupNameLink(groupName);

        EDIT_GROUP.verifyAtView()
                .selectUser(username)
                .clickAddUserButton()
                .verifyUserListed(username)
                .clickUserLink(username);

        EDIT_USER.verifyAtView()
                .verifyGroupListed(groupName)
                .clickGroupLink(groupName);

        EDIT_GROUP.verifyAtView()
                .clickRemoveUserButton(username)
                .verifyUserNotListed(username);
    }
}
