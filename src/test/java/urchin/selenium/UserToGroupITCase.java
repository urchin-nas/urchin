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

        newGroupView.goTo()
                .fillGroupName(groupName)
                .clickCreateGroupButton();

        groupsView.verifyGroupNameListed(groupName);

        newUserView.goTo()
                .fillUsername(username)
                .fillPassword(randomAlphanumeric(10))
                .clickCreateUserButton();

        usersView.verifyUsernameListed(username);
    }

    @AfterClass
    public static void tearDown() {
        usersView.goTo()
                .clickUsernameLink(username);

        editUserView.verifyAtView()
                .clickDeleteUserButton();

        groupsView.goTo()
                .clickGroupNameLink(groupName);

        editGroupView.verifyAtView()
                .clickDeleteGroupButton();
    }

    @Test
    public void userCanBeAddedAndRemovedFromUserView() {
        usersView.goTo()
                .verifyUsernameListed(username)
                .clickUsernameLink(username);

        editUserView.verifyAtView()
                .selectGroup(groupName)
                .clickAddGroupButton()
                .verifyGroupListed(groupName)
                .clickGroupLink(groupName);

        editGroupView.verifyAtView()
                .verifyUserListed(username)
                .clickUserLink(username);

        editUserView.verifyAtView()
                .clickRemoveGroupButton(groupName)
                .verifyGroupNotListed(groupName);
    }

    @Test
    public void userCanBeAddedAndRemovedFromGroupView() {
        groupsView.goTo()
                .verifyGroupNameListed(groupName)
                .clickGroupNameLink(groupName);

        editGroupView.verifyAtView()
                .selectUser(username)
                .clickAddUserButton()
                .verifyUserListed(username)
                .clickUserLink(username);

        editUserView.verifyAtView()
                .verifyGroupListed(groupName)
                .clickGroupLink(groupName);

        editGroupView.verifyAtView()
                .clickRemoveUserButton(username)
                .verifyUserNotListed(username);
    }
}
