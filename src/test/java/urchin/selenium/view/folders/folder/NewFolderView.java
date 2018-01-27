package urchin.selenium.view.folders.folder;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import urchin.selenium.view.PageView;

public class NewFolderView extends PageView<NewFolderView> {

    @Override
    protected String viewUrl() {
        return "/folders/new";
    }

    @Override
    public NewFolderView verifyAtView() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(byDataView("newFolder")));
        return this;
    }

    public NewFolderView fillFolderPath(String folderPath) {
        driver.findElement(By.name("folder")).sendKeys(folderPath);
        return this;
    }

    public void clickOnCreateFolder() {
        driver.findElement(byDataView("create")).click();
    }

    public void clickOnCancel() {
        driver.findElement(byDataView("cancel")).click();
    }
}
