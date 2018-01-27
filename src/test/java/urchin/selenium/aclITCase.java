package urchin.selenium;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import urchin.selenium.testutil.SeleniumTest;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static urchin.testutil.UnixUserAndGroupCleanup.GROUP_PREFIX;
import static urchin.testutil.UnixUserAndGroupCleanup.USERNAME_PREFIX;

public class aclITCase extends SeleniumTest {

    @ClassRule
    public static TemporaryFolder temporaryFolder = new TemporaryFolder();

    private static String groupName;
    private static String username;
    private static String folderName;
    private static String folderPath;

    @BeforeClass
    public static void setUp() {
        groupName = GROUP_PREFIX + System.currentTimeMillis();
        username = USERNAME_PREFIX + System.currentTimeMillis();
        folderName = "test-" + System.currentTimeMillis();
        folderPath = temporaryFolder.getRoot().getAbsolutePath() + "/" + folderName;

        NEW_GROUP.goTo()
                .fillGroupName(groupName)
                .clickOnCreateGroup();

        GROUPS.verifyGroupNameListed(groupName);

        NEW_USER.goTo()
                .fillUsername(username)
                .fillPassword(randomAlphanumeric(10))
                .clickOnCreateUser();

        USERS.verifyUsernameListed(username);

        NEW_FOLDER.goTo()
                .fillFolderPath(folderPath)
                .clickOnCreateFolder();

        FOLDERS.verifyFolderListed(folderName);
    }

    @AfterClass
    public static void tearDown() {
        USERS.goTo()
                .clickOnUsername(username);

        EDIT_USER.verifyAtView()
                .clickOnDeleteUser();

        GROUPS.goTo()
                .clickOnGroupName(groupName);

        EDIT_GROUP.verifyAtView()
                .clickOnDeleteGroup();

        FOLDERS.goTo()
                .clickOnFolder(folderName);

        EDIT_FOLDER.verifyAtView()
                .clickOnDeleteFolder();
    }

    @Test
    public void addingAndRemovingACLForUser() {

        FOLDERS.goTo()
                .clickOnFolder(folderName);

        EDIT_FOLDER.verifyAtView()
                .verifyUsernameListed(username)
                .verifyUserAclReadPermission(username, false)
                .verifyUserAclWritePermission(username, false)
                .verifyUserAclExecutePermission(username, false)
                .clickOnUserAclReadPermission(username)
                .clickOnUserAclWritePermission(username)
                .clickOnUserAclExecutePermission(username)
                .verifyUserAclReadPermission(username, true)
                .verifyUserAclWritePermission(username, true)
                .verifyUserAclExecutePermission(username, true)
                .clickOnUserAclReadPermission(username)
                .clickOnUserAclWritePermission(username)
                .clickOnUserAclExecutePermission(username)
                .verifyUserAclReadPermission(username, false)
                .verifyUserAclWritePermission(username, false)
                .verifyUserAclExecutePermission(username, false);
    }

    @Test
    public void addingAndRemovingACLForGroup() {

        FOLDERS.goTo()
                .clickOnFolder(folderName);

        EDIT_FOLDER.verifyAtView()
                .verifyGroupNameListed(groupName)
                .verifyGroupAclReadPermission(groupName, false)
                .verifyGroupAclWritePermission(groupName, false)
                .verifyGroupAclExecutePermission(groupName, false)
                .clickOnGroupAclReadPermission(groupName)
                .clickOnGroupAclWritePermission(groupName)
                .clickOnGroupAclExecutePermission(groupName)
                .verifyGroupAclReadPermission(groupName, true)
                .verifyGroupAclWritePermission(groupName, true)
                .verifyGroupAclExecutePermission(groupName, true)
                .clickOnGroupAclReadPermission(groupName)
                .clickOnGroupAclWritePermission(groupName)
                .clickOnGroupAclExecutePermission(groupName)
                .verifyGroupAclReadPermission(groupName, false)
                .verifyGroupAclWritePermission(groupName, false)
                .verifyGroupAclExecutePermission(groupName, false);
    }
}
