package urchin.model.permission;

import org.immutables.value.Value;
import urchin.model.group.Group;
import urchin.model.user.User;

import java.util.Map;

@Value.Immutable
public interface AclPermissions {

    Map<Group, AclPermission> getGroups();

    Map<User, AclPermission> getUsers();
}
