package urchin.selenium.view.users.user;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.boot.test.context.TestComponent;
import urchin.selenium.view.PageView;

@TestComponent
public class EditUserView extends PageView {

    @Override
    public EditUserView verifyAtView() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("edit-user")));
        return this;
    }

    public void clickDeleteUserButton() {
        driver.findElement(By.id("edit-user__delete-btn")).click();
    }
}
