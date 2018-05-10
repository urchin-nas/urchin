package urchin.selenium.view.setup;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import urchin.selenium.view.PageView;

public class SetupAdminView extends PageView<SetupAdminView> {

    @Override
    protected String viewUrl() {
        return "/setup-admin";
    }

    @Override
    public SetupAdminView verifyAtView() {

        waitUntil(ExpectedConditions.visibilityOfElementLocated(byDataView("setupAdmin")));
        return this;
    }

    public SetupAdminView fillUsername(String username) {
        driver.findElement(By.name("username")).sendKeys(username);
        return this;
    }

    public void clickOnAddAdmin() {
        driver.findElement(byDataView("add")).click();
    }

}
