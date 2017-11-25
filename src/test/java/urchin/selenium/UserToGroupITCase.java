package urchin.selenium;

import org.junit.Test;
import urchin.selenium.testutil.SeleniumTestApplication;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static urchin.testutil.UnixUserAndGroupCleanup.GROUP_PREFIX;
import static urchin.testutil.UnixUserAndGroupCleanup.USERNAME_PREFIX;

public class UserToGroupITCase extends SeleniumTestApplication {

    @Test
    public void userCanBeAddedAndRemovedFromGroup() {
        String groupName = GROUP_PREFIX + System.currentTimeMillis();
        String username = USERNAME_PREFIX + System.currentTimeMillis();

        newGroupView.goTo()
                .fillGroupName(groupName)
                .clickCreateGroupButton();

        newUserView.goTo()
                .fillUsername(username)
                .fillPassword(randomAlphanumeric(10))
                .clickCreateUserButton();

        usersView.verifyUsernameListed(username)
                .clickUsernameLink(username);

        editUserView.selectGroup(groupName)
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
}
