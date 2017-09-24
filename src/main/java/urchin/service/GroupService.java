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
import java.util.Optional;

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
    public GroupId addGroup(Group group) {
        GroupId groupId = groupRepository.saveGroup(group);
        groupCli.addGroup(group);
        return groupId;
    }

    @Transactional
    public void removeGroup(GroupId groupId) {
        Optional<Group> groupOptional = groupRepository.getGroup(groupId);
        if (groupOptional.isPresent()) {
            groupRepository.removeGroup(groupId);
            groupCli.removeGroup(groupOptional.get());
        } else {
            throw new IllegalArgumentException("Invalid GroupId: " + groupId.getId());
        }
    }

    public void addUserToGroup(UserId userId, GroupId groupId) {
        User user = userService.getUser(userId);
        Optional<Group> groupOptional = groupRepository.getGroup(groupId);

        if (groupOptional.isPresent()) {
            groupCli.addUserToGroup(user, groupOptional.get());
        } else {
            throw new IllegalArgumentException(String.format("Invalid UserId %s and/or GroupId %s", userId, groupId));
        }
    }

    public void removeUserFromGroup(UserId userId, GroupId groupId) {
        User user = userService.getUser(userId);
        Optional<Group> groupOptional = groupRepository.getGroup(groupId);

        if (groupOptional.isPresent()) {
            groupCli.removeUserFromGroup(user, groupOptional.get());
        } else {
            throw new IllegalArgumentException(String.format("Invalid UserId %s and/or GroupId %s", userId, groupId));
        }
    }

    public List<Group> getGroups() {
        return groupRepository.getGroups();
    }

    public Optional<Group> getGroup(GroupId groupId) {
        return groupRepository.getGroup(groupId);
    }
}
