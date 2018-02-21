package urchin.selenium;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import urchin.selenium.testutil.SeleniumTest;

public class FolderITCase extends SeleniumTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void creatingAndDeletingFolder() {
        String folderName = "test-" + System.currentTimeMillis();
        String folderPath = temporaryFolder.getRoot().getAbsolutePath() + "/" + folderName;

        HOME.goTo();

        NAVIGATION.verifyAtView()
                .clickOnFolders();

        FOLDERS.verifyAtView()
                .clickOnCreateNewFolder();

        NEW_FOLDER.verifyAtView()
                .clickOnCancel();

        FOLDERS.verifyAtView()
                .clickOnCreateNewFolder();

        NEW_FOLDER.verifyAtView()
                .fillFolderPath(folderPath)
                .clickOnCreateFolder();

        CONFIRM_NEW_FOLDER.verifyAtView()
                .fillPassphrase()
                .clickOnConfirm();

        EDIT_FOLDER.verifyAtView()
                .clickOnBack();

        FOLDERS.verifyAtView()
                .verifyFolderListed(folderName)
                .clickOnFolder(folderName);

        EDIT_FOLDER.verifyAtView()
                .clickOnDeleteFolder();

        FOLDERS.verifyAtView()
                .verifyFolderNotListed(folderName);
    }
}
