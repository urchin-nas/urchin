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
                .clickOnGroups();

        GROUPS.verifyAtView()
                .clickOnCreateNewGroup();

        NEW_GROUP.verifyAtView()
                .clickOnCancel();

        GROUPS.verifyAtView()
                .clickOnCreateNewGroup();

        NEW_GROUP.verifyAtView()
                .fillGroupName(groupName)
                .clickOnCreateGroup();

        GROUPS.verifyAtView()
                .verifyGroupNameListed(groupName)
                .clickOnGroupName(groupName);

        EDIT_GROUP.verifyAtView()
                .clickOnBack();

        GROUPS.verifyAtView()
                .verifyGroupNameListed(groupName)
                .clickOnGroupName(groupName);

        EDIT_GROUP.verifyAtView()
                .clickOnDeleteGroup();

        GROUPS.verifyAtView()
                .verifyGroupNameNotListed(groupName);

    }
}
