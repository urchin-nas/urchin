package urchin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urchin.cli.group.AddGroupCommand;
import urchin.cli.group.RemoveGroupCommand;
import urchin.domain.GroupRepository;
import urchin.domain.model.Group;
import urchin.domain.model.GroupId;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final AddGroupCommand addGroupCommand;
    private final RemoveGroupCommand removeGroupCommand;

    @Autowired
    public GroupService(GroupRepository groupRepository, AddGroupCommand addGroupCommand, RemoveGroupCommand removeGroupCommand) {
        this.groupRepository = groupRepository;
        this.addGroupCommand = addGroupCommand;
        this.removeGroupCommand = removeGroupCommand;
    }

    @Transactional
    public GroupId addGroup(Group group) {
        GroupId groupId = groupRepository.saveGroup(group);
        addGroupCommand.execute(group);
        return groupId;
    }

    @Transactional
    public void removeGroup(GroupId groupId) {
        Optional<Group> groupOptional = groupRepository.getGroup(groupId);
        if (groupOptional.isPresent()) {
            groupRepository.removeGroup(groupId);
            removeGroupCommand.execute(groupOptional.get());
        } else {
            throw new IllegalArgumentException("Invalid GroupId: " + groupId);
        }
    }

    public List<Group> getGroups() {
        return groupRepository.getGroups();
    }
}
