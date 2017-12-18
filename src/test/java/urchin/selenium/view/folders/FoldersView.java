package urchin.selenium.view.folders;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import urchin.selenium.view.PageView;

import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;

public class FoldersView extends PageView<FoldersView> {

    @Override
    protected String viewUrl() {
        return "/folders";
    }

    @Override
    public FoldersView verifyAtView() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(byDataView("folders")));
        return this;
    }

    public FoldersView verifyFolderListed(String folderName) {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.linkText(folderName)));
        return this;
    }

    public void clickOnCreateNewFolder() {
        driver.findElement(byDataView("newFolder")).click();
    }

    public void clickOnFolder(String folderName) {
        driver.findElement(By.linkText(folderName)).click();
    }

    public FoldersView verifyFolderNotListed(String folderName) {
        waitUntil(invisibilityOfElementLocated(By.linkText(folderName)));
        return this;
    }
}
