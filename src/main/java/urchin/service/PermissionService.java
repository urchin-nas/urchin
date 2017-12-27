package urchin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import urchin.cli.PermissionCli;
import urchin.model.folder.FolderId;
import urchin.model.folder.FolderSettings;
import urchin.model.group.Group;
import urchin.model.group.GroupId;
import urchin.model.permission.Acl;
import urchin.model.permission.AclPermission;
import urchin.model.user.User;
import urchin.model.user.UserId;
import urchin.repository.FolderSettingsRepository;
import urchin.repository.GroupRepository;
import urchin.repository.UserRepository;

@Service
public class PermissionService {

    private final Logger log = LoggerFactory.getLogger(PermissionService.class);
    private final FolderSettingsRepository folderSettingsRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final PermissionCli permissionCli;

    @Autowired
    public PermissionService(
            FolderSettingsRepository folderSettingsRepository,
            GroupRepository groupRepository,
            UserRepository userRepository,
            PermissionCli permissionCli) {
        this.folderSettingsRepository = folderSettingsRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.permissionCli = permissionCli;
    }

    public Acl getAcl(FolderId folderId) {
        FolderSettings folderSettings = folderSettingsRepository.getFolderSettings(folderId);
        return permissionCli.getAcl(folderSettings.getFolder().getPath());
    }

    public void setAcl(FolderId folderId, UserId userId, AclPermission aclPermission) {
        FolderSettings folderSettings = folderSettingsRepository.getFolderSettings(folderId);
        User user = userRepository.getUser(userId);
        log.info("Setting acl permissions {} for {} on {}", aclPermission, user, folderSettings.getFolder());
        permissionCli.setAclUserPermissions(folderSettings.getFolder().getPath(), user.getUsername(), aclPermission);
    }

    public void setAcl(FolderId folderId, GroupId groupId, AclPermission aclPermission) {
        FolderSettings folderSettings = folderSettingsRepository.getFolderSettings(folderId);
        Group group = groupRepository.getGroup(groupId);
        log.info("Setting acl permissions {} for {} on {}", aclPermission, group, folderSettings.getFolder());
        permissionCli.setAclGroupPermissions(folderSettings.getFolder().getPath(), group.getName(), aclPermission);
    }
}
