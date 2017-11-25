package urchin.selenium;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import urchin.selenium.testutil.SeleniumTestApplication;
import urchin.selenium.view.HomeView;
import urchin.selenium.view.MenuView;
import urchin.selenium.view.groups.GroupsView;
import urchin.selenium.view.groups.group.EditGroupView;
import urchin.selenium.view.groups.group.NewGroupView;

import static urchin.testutil.UnixUserAndGroupCleanup.GROUP_PREFIX;

public class GroupITCase extends SeleniumTestApplication {

    @Autowired
    private HomeView homeView;
    @Autowired
    private MenuView menuView;
    @Autowired
    private GroupsView groupsView;
    @Autowired
    private NewGroupView newGroupView;
    @Autowired
    private EditGroupView editGroupView;

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
