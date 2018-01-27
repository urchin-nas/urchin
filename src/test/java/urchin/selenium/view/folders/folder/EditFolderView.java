package urchin.selenium.view.folders.folder;

import org.openqa.selenium.support.ui.ExpectedConditions;
import urchin.selenium.view.PageView;

public class EditFolderView extends PageView<EditFolderView> {

    private static final String READ = "-read";
    private static final String WRITE = "-write";
    private static final String EXECUTE = "-execute";

    @Override
    protected String viewUrl() {
        return "/folders/edit/0";
    }

    @Override
    public EditFolderView verifyAtView() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(byDataView("editFolder")));
        return this;
    }

    public void clickOnBack() {
        driver.findElement(byDataView("back")).click();
    }

    public void clickOnDeleteFolder() {
        driver.findElement(byDataView("delete")).click();
    }

    public EditFolderView verifyUsernameListed(String username) {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(byDataView(username)));
        return this;
    }

    public EditFolderView clickOnUserAclReadPermission(String username) {
        return clickAclPermission(username, READ);
    }

    public EditFolderView clickOnUserAclWritePermission(String username) {
        return clickAclPermission(username, WRITE);
    }

    public EditFolderView clickOnUserAclExecutePermission(String username) {
        return clickAclPermission(username, EXECUTE);
    }

    public EditFolderView verifyUserAclReadPermission(String username, boolean expected) {
        return verifyAclPermission(username, expected, READ);
    }

    public EditFolderView verifyUserAclWritePermission(String username, boolean expected) {
        return verifyAclPermission(username, expected, WRITE);
    }

    public EditFolderView verifyUserAclExecutePermission(String username, boolean expected) {
        return verifyAclPermission(username, expected, EXECUTE);
    }

    public EditFolderView verifyGroupNameListed(String GroupName) {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(byDataView(GroupName)));
        return this;
    }

    public EditFolderView clickOnGroupAclReadPermission(String GroupName) {
        return clickAclPermission(GroupName, READ);
    }

    public EditFolderView clickOnGroupAclWritePermission(String GroupName) {
        return clickAclPermission(GroupName, WRITE);
    }

    public EditFolderView clickOnGroupAclExecutePermission(String GroupName) {
        return clickAclPermission(GroupName, EXECUTE);
    }

    public EditFolderView verifyGroupAclReadPermission(String GroupName, boolean expected) {
        return verifyAclPermission(GroupName, expected, READ);
    }

    public EditFolderView verifyGroupAclWritePermission(String GroupName, boolean expected) {
        return verifyAclPermission(GroupName, expected, WRITE);
    }

    public EditFolderView verifyGroupAclExecutePermission(String GroupName, boolean expected) {
        return verifyAclPermission(GroupName, expected, EXECUTE);
    }

    private EditFolderView clickAclPermission(String name, String permission) {
        driver.findElement(byDataView(name + permission)).click();
        return this;
    }

    private EditFolderView verifyAclPermission(String name, boolean expected, String permission) {
        waitUntil(ExpectedConditions.elementSelectionStateToBe(byDataView(name + permission), expected));
        return this;
    }
}
