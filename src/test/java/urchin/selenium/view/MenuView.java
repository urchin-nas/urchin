package urchin.selenium.view;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class MenuView extends PageView {

    @Override
    public MenuView verifyAtView() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("main-menu")));
        return this;
    }

    public void clickUsersLink() {
        driver.findElement(By.id("main-menu__users")).click();
    }

}
