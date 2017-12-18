package urchin.selenium.view.groups;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import urchin.selenium.view.PageView;

import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;

public class GroupsView extends PageView<GroupsView> {

    @Override
    protected String viewUrl() {
        return "/groups";
    }

    @Override
    public GroupsView verifyAtView() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(byDataView("groups")));
        return this;
    }

    public GroupsView verifyGroupNameListed(String groupName) {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.linkText(groupName)));
        return this;
    }

    public GroupsView verifyGroupNameNotListed(String groupName) {
        waitUntil(invisibilityOfElementLocated(By.linkText(groupName)));
        return this;
    }

    public void clickOnCreateNewGroup() {
        driver.findElement(byDataView("newGroup")).click();
    }

    public void clickOnGroupName(String groupName) {
        driver.findElement(By.linkText(groupName)).click();
    }

}
