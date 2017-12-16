package urchin.selenium;

import org.junit.Test;
import urchin.selenium.testutil.SeleniumTest;

import static urchin.testutil.UnixUserAndGroupCleanup.GROUP_PREFIX;

public class GroupITCase extends SeleniumTest {

    @Test
    public void createAndDeleteGroup() {
        String groupName = GROUP_PREFIX + System.currentTimeMillis();

        HOME.goTo();

        NAVIGATION.verifyAtView()
                .clickGroupsLink();

        GROUPS.verifyAtView()
                .clickCreateNewGroupLink();

        NEW_GROUP.verifyAtView()
                .clickCancelButton();

        GROUPS.verifyAtView()
                .clickCreateNewGroupLink();

        NEW_GROUP.verifyAtView()
                .fillGroupName(groupName)
                .clickCreateGroupButton();

        GROUPS.verifyAtView()
                .verifyGroupNameListed(groupName)
                .clickGroupNameLink(groupName);

        EDIT_GROUP.verifyAtView()
                .clickBackButton();

        GROUPS.verifyAtView()
                .verifyGroupNameListed(groupName)
                .clickGroupNameLink(groupName);

        EDIT_GROUP.verifyAtView()
                .clickDeleteGroupButton();

        GROUPS.verifyAtView()
                .verifyGroupNameNotListed(groupName);

    }
}
