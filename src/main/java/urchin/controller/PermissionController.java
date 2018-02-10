package urchin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import urchin.controller.api.ImmutableMessageResponse;
import urchin.controller.api.MessageResponse;
import urchin.controller.api.permission.AclResponse;
import urchin.controller.api.permission.SetAclGroupPermissionRequest;
import urchin.controller.api.permission.SetAclUserPermissionRequest;
import urchin.model.folder.FolderId;
import urchin.model.group.GroupId;
import urchin.model.permission.ImmutableAclPermission;
import urchin.model.user.UserId;
import urchin.service.PermissionService;

import javax.validation.Valid;

import static urchin.controller.mapper.AclPermissionsMapper.mapToAclResponse;

@RestController
@RequestMapping("api/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @RequestMapping(value = "/acl/{folderId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public AclResponse getAcl(@PathVariable int folderId) {
        return mapToAclResponse(permissionService.getAcl(FolderId.of(folderId)));
    }

    @RequestMapping(value = "acl/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageResponse setAclForUser(@Valid @RequestBody SetAclUserPermissionRequest setAclUserPermissionRequest) {
        permissionService.setAcl(
                FolderId.of(setAclUserPermissionRequest.getFolderId()),
                UserId.of(setAclUserPermissionRequest.getUserId()),
                parsePermissions(setAclUserPermissionRequest)
        );

        return ImmutableMessageResponse.of("User permissions set");
    }

    @RequestMapping(value = "acl/group", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageResponse setAclForGroup(@Valid @RequestBody SetAclGroupPermissionRequest setAclGroupPermissionRequest) {
        permissionService.setAcl(
                FolderId.of(setAclGroupPermissionRequest.getFolderId()),
                GroupId.of(setAclGroupPermissionRequest.getGroupId()),
                parsePermissions(setAclGroupPermissionRequest)
        );
        return ImmutableMessageResponse.of("Group permissions set");
    }

    private ImmutableAclPermission parsePermissions(SetAclUserPermissionRequest setAclUserPermissionRequest) {
        return getImmutableAclPermission(setAclUserPermissionRequest.read(), setAclUserPermissionRequest.write(), setAclUserPermissionRequest.execute());
    }

    private ImmutableAclPermission parsePermissions(SetAclGroupPermissionRequest setAclGroupPermissionRequest) {
        return getImmutableAclPermission(setAclGroupPermissionRequest.read(), setAclGroupPermissionRequest.write(), setAclGroupPermissionRequest.execute());
    }

    private ImmutableAclPermission getImmutableAclPermission(boolean read, boolean write, boolean execute) {
        String permissions = "";
        permissions += read ? "r" : "-";
        permissions += write ? "w" : "-";
        permissions += execute ? "x" : "-";

        return ImmutableAclPermission.builder()
                .permissions(permissions)
                .build();
    }
}
