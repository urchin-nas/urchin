package urchin.model.permission;

import org.immutables.value.Value;
import urchin.model.group.GroupName;
import urchin.model.user.Username;

import java.util.Map;

@Value.Immutable
public interface Acl {

    Map<GroupName, AclPermission> getGroups();

    Map<Username, AclPermission> getUsers();
}
