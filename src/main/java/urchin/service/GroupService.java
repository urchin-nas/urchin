package urchin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urchin.domain.GroupRepository;
import urchin.domain.cli.GroupCli;
import urchin.domain.model.Group;
import urchin.domain.model.GroupId;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupCli groupCli;

    @Autowired
    public GroupService(GroupRepository groupRepository, GroupCli groupCli) {
        this.groupRepository = groupRepository;
        this.groupCli = groupCli;
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
            throw new IllegalArgumentException("Invalid GroupId: " + groupId);
        }
    }

    public List<Group> getGroups() {
        return groupRepository.getGroups();
    }
}
