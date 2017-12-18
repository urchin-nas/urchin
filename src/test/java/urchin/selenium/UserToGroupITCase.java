package urchin.selenium;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import urchin.selenium.testutil.SeleniumTest;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static urchin.testutil.UnixUserAndGroupCleanup.GROUP_PREFIX;
import static urchin.testutil.UnixUserAndGroupCleanup.USERNAME_PREFIX;

public class UserToGroupITCase extends SeleniumTest {

    private static String groupName;
    private static String username;

    @BeforeClass
    public static void setUp() {
        groupName = GROUP_PREFIX + System.currentTimeMillis();
        username = USERNAME_PREFIX + System.currentTimeMillis();

        NEW_GROUP.goTo()
                .fillGroupName(groupName)
                .clickOnCreateGroup();

        GROUPS.verifyGroupNameListed(groupName);

        NEW_USER.goTo()
                .fillUsername(username)
                .fillPassword(randomAlphanumeric(10))
                .clickOnCreateUser();

        USERS.verifyUsernameListed(username);
    }

    @AfterClass
    public static void tearDown() {
        USERS.goTo()
                .clickOnUsername(username);

        EDIT_USER.verifyAtView()
                .clickOnDeleteUser();

        GROUPS.goTo()
                .clickOnGroupName(groupName);

        EDIT_GROUP.verifyAtView()
                .clickOnDeleteGroup();
    }

    @Test
    public void userCanBeAddedAndRemovedFromUserView() {
        USERS.goTo()
                .verifyUsernameListed(username)
                .clickOnUsername(username);

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
                .clickOnGroupName(groupName);

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
