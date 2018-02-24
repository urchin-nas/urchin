package urchin.cli;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import urchin.model.group.GroupName;
import urchin.model.permission.*;
import urchin.model.user.Username;
import urchin.testutil.CliTestConfiguration;
import urchin.testutil.UnixUserAndGroupCleanup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static urchin.testutil.UnixUserAndGroupCleanup.GROUP_PREFIX;
import static urchin.testutil.UnixUserAndGroupCleanup.USERNAME_PREFIX;

@RunWith(SpringRunner.class)
@Import(CliTestConfiguration.class)
public class PermissionCliIT {

    private static final String TEST_FILE = "test_file";

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Rule
    @Autowired
    public UnixUserAndGroupCleanup unixUserAndGroupCleanup;

    @Autowired
    private PermissionCli permissionCli;

    @Autowired
    private UserCli userCli;

    @Autowired
    private GroupCli groupCli;

    private Path testFile;
    private GroupName groupName;
    private Username username;

    @Before
    public void setup() throws IOException {
        testFile = createFileInFolder(TEST_FILE, temporaryFolder.getRoot().toPath());
        groupName = GroupName.of(GROUP_PREFIX + System.currentTimeMillis());
        username = Username.of(USERNAME_PREFIX + System.currentTimeMillis());
        userCli.addUser(username);
        groupCli.addGroup(groupName);
    }

    @Test
    public void filePermissionsAreChanged() {
        FileModes fileModes = permissionCli.getFileModes(testFile);
        System.out.println("fileModes = " + fileModes);

        FileModes modes = ImmutableFileModes.builder()
                .owner(7)
                .group(7)
                .other(7)
                .build();
        permissionCli.changeFileMode(modes, testFile);
        FileModes newFileModes = permissionCli.getFileModes(testFile);

        assertThat(newFileModes).isNotEqualTo(fileModes);
    }

    @Test
    public void ownerIsChanged() {
        FileOwners fileOwners = permissionCli.getFileOwners(testFile);
        permissionCli.changeOwner(testFile, username, groupName);

        FileOwners newFileOwners = permissionCli.getFileOwners(testFile);

        assertThat(newFileOwners).isNotEqualTo(fileOwners);
    }

    @Test
    public void setAndGetAclOnFile() {
        ImmutableAclPermission groupAclPermission = ImmutableAclPermission.of("rwx");
        ImmutableAclPermission userAclPermission = ImmutableAclPermission.of("r--");
        permissionCli.setAclGroupPermissions(testFile, groupName, groupAclPermission);
        permissionCli.setAclUserPermissions(testFile, username, userAclPermission);

        Acl acl = permissionCli.getAcl(testFile);

        assertThat(acl.getGroups()).containsKey(groupName);
        assertThat(acl.getGroups().get(groupName)).isEqualTo(groupAclPermission);
        assertThat(acl.getUsers()).containsKey(username);
        assertThat(acl.getUsers().get(username)).isEqualTo(userAclPermission);
    }

    @Test
    public void setAndGetAclOnFolder() {
        Path folder = temporaryFolder.getRoot().toPath();
        ImmutableAclPermission groupAclPermission = ImmutableAclPermission.of("rwx");
        ImmutableAclPermission userAclPermission = ImmutableAclPermission.of("r--");
        permissionCli.setAclGroupPermissions(folder, groupName, groupAclPermission);
        permissionCli.setAclUserPermissions(folder, username, userAclPermission);

        Acl acl = permissionCli.getAcl(folder);

        assertThat(acl.getGroups()).containsKey(groupName);
        assertThat(acl.getGroups().get(groupName)).isEqualTo(groupAclPermission);
        assertThat(acl.getUsers()).containsKey(username);
        assertThat(acl.getUsers().get(username)).isEqualTo(userAclPermission);
    }

    private Path createFileInFolder(String filename, Path folder) throws IOException {
        Path file = Paths.get(folder.toAbsolutePath().toString() + "/" + filename);
        return Files.createFile(file);
    }

}