package urchin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urchin.cli.GroupCli;
import urchin.model.group.Group;
import urchin.model.group.GroupId;
import urchin.model.group.GroupName;
import urchin.model.user.User;
import urchin.model.user.UserId;
import urchin.model.user.Username;
import urchin.repository.GroupRepository;
import urchin.repository.UserRepository;

import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final GroupCli groupCli;
    private final UserService userService;

    @Autowired
    public GroupService(
            GroupRepository groupRepository,
            UserRepository userRepository,
            GroupCli groupCli,
            UserService userService
    ) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.groupCli = groupCli;
        this.userService = userService;
    }

    @Transactional
    public GroupId addGroup(GroupName groupName) {
        GroupId groupId = groupRepository.saveGroup(groupName);
        groupCli.addGroup(groupName);
        return groupId;
    }

    @Transactional
    public void removeGroup(GroupId groupId) {
        Group group = groupRepository.getGroup(groupId);
        groupRepository.removeGroup(groupId);
        groupCli.removeGroup(group.getName());
    }

    public void addUserToGroup(UserId userId, GroupId groupId) {
        User user = userService.getUser(userId);
        Group group = groupRepository.getGroup(groupId);
        groupCli.addUserToGroup(user, group);
    }

    public void removeUserFromGroup(UserId userId, GroupId groupId) {
        User user = userService.getUser(userId);
        Group group = groupRepository.getGroup(groupId);
        groupCli.removeUserFromGroup(user, group);
    }

    public List<Group> getGroups() {
        return groupRepository.getGroups();
    }

    public Group getGroup(GroupId groupId) {
        return groupRepository.getGroup(groupId);
    }

    public List<User> listUsersForGroup(GroupId groupId) {
        Group group = groupRepository.getGroup(groupId);
        List<Username> usernames = groupCli.listUsersForGroup(group);
        return userRepository.getUsersByUsername(usernames);
    }
}
