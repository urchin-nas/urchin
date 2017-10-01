package urchin.cli;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import urchin.model.FileModes;
import urchin.model.FileOwners;
import urchin.model.ImmutableFileModes;
import urchin.testutil.CliTestConfiguration;
import urchin.testutil.UnixUserAndGroupCleanup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertNotEquals;
import static urchin.testutil.UnixUserAndGroupCleanup.GROUP_PREFIX;
import static urchin.testutil.UnixUserAndGroupCleanup.USERNAME_PREFIX;

@RunWith(SpringRunner.class)
@Import(CliTestConfiguration.class)
public class PermissionCliTest {

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

    @Before
    public void setup() throws IOException {
        testFile = createFileInFolder(TEST_FILE, temporaryFolder.getRoot().toPath());
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

        assertNotEquals(fileModes, newFileModes);
    }

    @Test
    public void ownerIsChanged() {
        String groupName = GROUP_PREFIX + System.currentTimeMillis();
        String username = USERNAME_PREFIX + System.currentTimeMillis();
        userCli.addUser(USERNAME_PREFIX + System.currentTimeMillis());
        groupCli.addGroup(groupName);

        FileOwners fileOwners = permissionCli.getFileOwners(testFile);
        permissionCli.changeOwner(testFile, username, groupName);

        FileOwners newFileOwners = permissionCli.getFileOwners(testFile);

        assertNotEquals(fileOwners, newFileOwners);
    }


    private Path createFileInFolder(String filename, Path folder) throws IOException {
        Path file = Paths.get(folder.toAbsolutePath().toString() + "/" + filename);
        return Files.createFile(file);
    }

}