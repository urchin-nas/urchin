package urchin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urchin.domain.cli.GroupCli;
import urchin.domain.model.Group;
import urchin.domain.model.GroupId;
import urchin.domain.model.User;
import urchin.domain.model.UserId;
import urchin.domain.repository.GroupRepository;

import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupCli groupCli;
    private final UserService userService;

    @Autowired
    public GroupService(GroupRepository groupRepository, GroupCli groupCli, UserService userService) {
        this.groupRepository = groupRepository;
        this.groupCli = groupCli;
        this.userService = userService;
    }

    @Transactional
    public GroupId addGroup(String groupName) {
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
}
