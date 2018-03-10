package urchin.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import urchin.cli.PermissionCli;
import urchin.model.folder.*;
import urchin.model.group.Group;
import urchin.model.group.GroupId;
import urchin.model.group.GroupName;
import urchin.model.group.ImmutableGroup;
import urchin.model.permission.*;
import urchin.model.user.ImmutableUser;
import urchin.model.user.User;
import urchin.model.user.UserId;
import urchin.model.user.Username;
import urchin.repository.FolderSettingsRepository;
import urchin.repository.GroupRepository;
import urchin.repository.UserRepository;

import java.nio.file.Paths;
import java.time.LocalDateTime;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PermissionServiceTest {

    private static final FolderId FOLDER_ID = FolderId.of(1);
    private static final UserId USER_ID = UserId.of(2);
    private static final GroupId GROUP_ID = GroupId.of(3);
    private static final AclPermission ACL_PERMISSION = ImmutableAclPermission.of("rwx");
    private static final Username USERNAME = Username.of("username");
    private static final GroupName GROUP_NAME = GroupName.of("group name");
    private static final AclPermission GROUP_ACL_PERMISSION = ImmutableAclPermission.of("rwx");
    private static final AclPermission USER_ACL_PERMISSION = ImmutableAclPermission.of("---");

    private static final FolderSettings FOLDER_SETTINGS = ImmutableFolderSettings.builder()
            .folderId(FOLDER_ID)
            .folder(ImmutableFolder.of(Paths.get("/some/path")))
            .encryptedFolder(ImmutableEncryptedFolder.of(Paths.get("/some/.path")))
            .created(LocalDateTime.now())
            .isAutoMount(false)
            .build();

    private static final User USER = ImmutableUser.builder()
            .userId(USER_ID)
            .username(USERNAME)
            .created(LocalDateTime.now())
            .build();

    private static final Group GROUP = ImmutableGroup.builder()
            .groupId(GROUP_ID)
            .name(GROUP_NAME)
            .created(LocalDateTime.now())
            .build();

    private static final Acl ACL = ImmutableAcl.builder()
            .putGroups(GROUP_NAME, GROUP_ACL_PERMISSION)
            .putUsers(USERNAME, USER_ACL_PERMISSION)
            .build();

    @Mock
    private FolderSettingsRepository folderSettingsRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PermissionCli permissionCli;

    @InjectMocks
    private PermissionService permissionService;


    @Test
    public void setUserPermissionsIsCalledWhenFolderAndUserExist() {
        when(folderSettingsRepository.getFolderSettings(FOLDER_ID)).thenReturn(FOLDER_SETTINGS);
        when(userRepository.getUser(USER_ID)).thenReturn(USER);

        permissionService.setAcl(FOLDER_ID, USER_ID, ACL_PERMISSION);

        verify(permissionCli).setAclUserPermissions(FOLDER_SETTINGS.getFolder().getPath(), USER.getUsername(), ACL_PERMISSION);
    }

    @Test
    public void setGroupPermissionsIsCalledWhenFolderAndUserExist() {
        when(folderSettingsRepository.getFolderSettings(FOLDER_ID)).thenReturn(FOLDER_SETTINGS);
        when(groupRepository.getGroup(GROUP_ID)).thenReturn(GROUP);

        permissionService.setAcl(FOLDER_ID, GROUP_ID, ACL_PERMISSION);

        verify(permissionCli).setAclGroupPermissions(FOLDER_SETTINGS.getFolder().getPath(), GROUP.getName(), ACL_PERMISSION);
    }

    @Test
    public void aclPermissionsAreReturnedForExistingFolderUserAndGroup() {
        when(folderSettingsRepository.getFolderSettings(FOLDER_ID)).thenReturn(FOLDER_SETTINGS);
        when(permissionCli.getAcl(FOLDER_SETTINGS.getFolder().getPath())).thenReturn(ACL);
        when(groupRepository.getGroupsByName(singletonList(GROUP_NAME))).thenReturn(singletonList(GROUP));
        when(userRepository.getUsersByUsername(singletonList(USERNAME))).thenReturn(singletonList(USER));

        AclPermissions aclPermissions = permissionService.getAcl(FOLDER_ID);

        assertThat(aclPermissions.getGroups()).hasSize(1);
        assertThat(aclPermissions.getUsers()).hasSize(1);
        assertThat(aclPermissions.getGroups().get(GROUP)).isEqualTo(GROUP_ACL_PERMISSION);
        assertThat(aclPermissions.getUsers().get(USER)).isEqualTo(USER_ACL_PERMISSION);
    }

    @Test
    public void aclPermissionsIsNotReturnedForUserIfUserIsNotInRepository() {
        when(folderSettingsRepository.getFolderSettings(FOLDER_ID)).thenReturn(FOLDER_SETTINGS);
        when(permissionCli.getAcl(FOLDER_SETTINGS.getFolder().getPath())).thenReturn(ACL);
        when(groupRepository.getGroupsByName(singletonList(GROUP_NAME))).thenReturn(singletonList(GROUP));

        AclPermissions aclPermissions = permissionService.getAcl(FOLDER_ID);

        assertThat(aclPermissions.getGroups()).hasSize(1);
        assertThat(aclPermissions.getUsers()).hasSize(0);
    }

    @Test
    public void aclPermissionsIsNotReturnedForGroupIfUserIsNotInRepository() {
        when(folderSettingsRepository.getFolderSettings(FOLDER_ID)).thenReturn(FOLDER_SETTINGS);
        when(permissionCli.getAcl(FOLDER_SETTINGS.getFolder().getPath())).thenReturn(ACL);
        when(userRepository.getUsersByUsername(singletonList(USERNAME))).thenReturn(singletonList(USER));

        AclPermissions aclPermissions = permissionService.getAcl(FOLDER_ID);

        assertThat(aclPermissions.getGroups()).hasSize(0);
        assertThat(aclPermissions.getUsers()).hasSize(1);
    }

}