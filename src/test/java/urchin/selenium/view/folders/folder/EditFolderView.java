package urchin.selenium.view.folders.folder;

import org.openqa.selenium.support.ui.ExpectedConditions;
import urchin.selenium.view.PageView;

public class EditFolderView extends PageView<EditFolderView> {

    @Override
    protected String viewUrl() {
        return "/folders/edit/0";
    }

    @Override
    public EditFolderView verifyAtView() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(byDataView("editFolder")));
        return this;
    }

    public void clickBackButton() {
        driver.findElement(byDataView("back")).click();
    }

    public void clickDeleteFólderButton() {
        driver.findElement(byDataView("delete")).click();
    }
}
