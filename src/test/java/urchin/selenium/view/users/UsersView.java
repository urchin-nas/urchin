package urchin.selenium.view.users;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.boot.test.context.TestComponent;
import urchin.selenium.view.PageView;

import static org.junit.Assert.assertTrue;

@TestComponent
public class UsersView extends PageView {

    @Override
    public UsersView verifyAtView() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("user-list")));
        return this;
    }

    public UsersView verifyUsernameListed(String username) {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.linkText(username)));
        return this;
    }

    public UsersView verifyUsernameNotListed(String username) {
        assertTrue(driver.findElements(By.linkText(username)).size() < 1);
        return this;
    }

    public void clickCreateNewUserLink() {
        driver.findElement(By.id("user-list__new-user")).click();
    }

    public void clickUsernameLink(String username) {
        driver.findElement(By.linkText(username)).click();
    }


}
