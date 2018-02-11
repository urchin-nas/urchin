package urchin.controller;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import urchin.controller.api.IdResponse;
import urchin.controller.api.MessageResponse;
import urchin.controller.api.folder.*;
import urchin.controller.api.group.AddGroupRequest;
import urchin.controller.api.group.ImmutableAddGroupRequest;
import urchin.controller.api.permission.*;
import urchin.controller.api.user.AddUserRequest;
import urchin.controller.api.user.ImmutableAddUserRequest;
import urchin.testutil.TemporaryFolderUnmount;
import urchin.testutil.TestApplication;

import java.nio.file.Paths;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static urchin.testutil.UnixUserAndGroupCleanup.GROUP_PREFIX;
import static urchin.testutil.UnixUserAndGroupCleanup.USERNAME_PREFIX;

public class PermissionControllerIT extends TestApplication {

    @Rule
    public TemporaryFolderUnmount temporaryFolderUnmount = new TemporaryFolderUnmount();

    private int userId;
    private int groupId;
    private int folderId;

    @Before
    public void setUp() {
        String tmpFolderPath = temporaryFolderUnmount.getRoot().getAbsolutePath();

        AddUserRequest addUserRequest = ImmutableAddUserRequest.builder()
                .username(USERNAME_PREFIX + System.currentTimeMillis())
                .password(randomAlphanumeric(10))
                .build();
        AddGroupRequest addGroupRequest = ImmutableAddGroupRequest.of(GROUP_PREFIX + System.currentTimeMillis());
        FolderRequest folderRequest = ImmutableFolderRequest.of(Paths.get(tmpFolderPath + "/testFolder").toAbsolutePath().toString());

        ResponseEntity<IdResponse> addUserResponse = addUserRequest(addUserRequest);
        ResponseEntity<IdResponse> addGroupResponse = addGroupRequest(addGroupRequest);
        ResponseEntity<CreatedFolderResponse> createFolderResponse = createFolderRequest(folderRequest);

        MountEncryptedFolderRequest mountEncryptedFolderRequest = ImmutableMountEncryptedFolderRequest.builder()
                .folderId(createFolderResponse.getBody().getId())
                .passphrase(createFolderResponse.getBody().getPassphrase())
                .build();

        mountEncryptedFolderRequest(mountEncryptedFolderRequest);

        userId = addUserResponse.getBody().getId();
        groupId = addGroupResponse.getBody().getId();
        folderId = createFolderResponse.getBody().getId();
    }

    @Test
    public void setAndGetAclPermissions() {
        SetAclGroupPermissionRequest setAclGroupPermissionRequest = ImmutableSetAclGroupPermissionRequest.builder()
                .folderId(folderId)
                .groupId(groupId)
                .read(true)
                .write(true)
                .execute(true)
                .build();

        SetAclUserPermissionRequest setAclUserPermissionRequest = ImmutableSetAclUserPermissionRequest.builder()
                .folderId(folderId)
                .userId(userId)
                .read(true)
                .write(true)
                .execute(true)
                .build();

        ResponseEntity<MessageResponse> setAclForGroupResponse = setAclForGroupRequest(setAclGroupPermissionRequest);

        assertThat(setAclForGroupResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<MessageResponse> setAclForUserResponse = setAclForUserRequest(setAclUserPermissionRequest);

        assertThat(setAclForUserResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<AclResponse> response = getAclRequest(folderId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        AclResponse aclResponse = response.getBody();
        assertThat(aclResponse.getGroups()).hasSize(1);
        assertThat(aclResponse.getUsers()).hasSize(1);

        AclGroupPermissionsResponse aclGroupPermissionsResponse = aclResponse.getGroups().get(0);
        assertThat(aclGroupPermissionsResponse.getGroup().getGroupId()).isEqualTo(groupId);
        assertThat(aclGroupPermissionsResponse.getAclPermissions().hasExecute()).isTrue();
        assertThat(aclGroupPermissionsResponse.getAclPermissions().hasRead()).isTrue();
        assertThat(aclGroupPermissionsResponse.getAclPermissions().hasWrite()).isTrue();

        AclUserPermissionsResponse aclUserPermissionsResponse = aclResponse.getUsers().get(0);
        assertThat(aclUserPermissionsResponse.getUser().getUserId()).isEqualTo(userId);
        assertThat(aclUserPermissionsResponse.getAclPermissions().hasExecute()).isTrue();
        assertThat(aclUserPermissionsResponse.getAclPermissions().hasRead()).isTrue();
        assertThat(aclUserPermissionsResponse.getAclPermissions().hasWrite()).isTrue();
    }

    private ResponseEntity<MessageResponse> setAclForUserRequest(SetAclUserPermissionRequest setAclUserPermissionRequest) {
        return testRestTemplate.postForEntity(discoverControllerPath() + "/acl/user", setAclUserPermissionRequest, MessageResponse.class);
    }

    private ResponseEntity<MessageResponse> setAclForGroupRequest(SetAclGroupPermissionRequest setAclUserPermissionRequest) {
        return testRestTemplate.postForEntity(discoverControllerPath() + "/acl/group", setAclUserPermissionRequest, MessageResponse.class);
    }

    private ResponseEntity<AclResponse> getAclRequest(int folderId) {
        return testRestTemplate.getForEntity(discoverControllerPath() + "/acl/" + folderId, AclResponse.class);
    }

    private ResponseEntity<IdResponse> addUserRequest(AddUserRequest addUserRequest) {
        return testRestTemplate.postForEntity(discoverControllerPath(UserController.class) + "/add", addUserRequest, IdResponse.class);
    }

    private ResponseEntity<IdResponse> addGroupRequest(AddGroupRequest addGroupRequest) {
        return testRestTemplate.postForEntity(discoverControllerPath(GroupController.class) + "/add", addGroupRequest, IdResponse.class);
    }

    private ResponseEntity<CreatedFolderResponse> createFolderRequest(FolderRequest folderRequest) {
        return testRestTemplate.postForEntity(discoverControllerPath(FolderController.class) + "/create", folderRequest, CreatedFolderResponse.class);
    }

    private ResponseEntity<MessageResponse> mountEncryptedFolderRequest(MountEncryptedFolderRequest mountEncryptedFolderRequest) {
        return testRestTemplate.postForEntity(discoverControllerPath(FolderController.class) + "/mount", mountEncryptedFolderRequest, MessageResponse.class);
    }
}