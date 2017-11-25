package urchin.selenium;

import org.junit.Test;
import urchin.selenium.testutil.SeleniumTestApplication;

import static urchin.testutil.UnixUserAndGroupCleanup.GROUP_PREFIX;

public class GroupITCase extends SeleniumTestApplication {

    @Test
    public void createAndDeleteGroup() {
        String groupName = GROUP_PREFIX + System.currentTimeMillis();

        homeView.goToView();

        menuView.verifyAtView()
                .clickGroupsLink();

        groupsView.verifyAtView()
                .clickCreateNewGroupLink();

        newGroupView.verifyAtView()
                .fillGroupName(groupName)
                .clickCreateGroupButton();

        groupsView.verifyAtView()
                .verifyGroupNameListed(groupName)
                .clickGroupnameLink(groupName);

        editGroupView.verifyAtView()
                .clickDeleteGroupButton();

        groupsView.verifyAtView()
                .verifyGroupNameNotListed(groupName);

    }
}
