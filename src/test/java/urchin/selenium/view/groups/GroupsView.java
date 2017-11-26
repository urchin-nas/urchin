package urchin.selenium.view.groups;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import urchin.selenium.view.PageView;

import static org.junit.Assert.assertTrue;

public class GroupsView extends PageView {

    @Override
    public GroupsView verifyAtView() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("group-list")));
        return this;
    }

    public GroupsView goTo() {
        driver.get(url + "/groups");
        return verifyAtView();
    }

    public GroupsView verifyGroupNameListed(String groupName) {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.linkText(groupName)));
        return this;
    }

    public GroupsView verifyGroupNameNotListed(String groupName) {
        assertTrue(driver.findElements(By.linkText(groupName)).size() < 1);
        return this;
    }

    public void clickCreateNewGroupLink() {
        driver.findElement(By.id("group-list__new-group")).click();
    }

    public void clickGroupnameLink(String groupName) {
        driver.findElement(By.linkText(groupName)).click();
    }

}
